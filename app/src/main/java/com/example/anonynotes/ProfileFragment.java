package com.example.anonynotes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    private ImageButton burgerButton;
    private Button account_status_btn, change_password_btn, logout_btn, editProfileButton;
    private TextView tvUsernameProfile, tvBioProfile;
    private LinearLayout menuOptions;
    private RecyclerView recyclerView;
    private ProfileAdapter profileAdapter;
    private List<Note> notesList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views
        burgerButton = view.findViewById(R.id.burgerButton);
        tvBioProfile = view.findViewById(R.id.tvBioProfile);
        tvUsernameProfile = view.findViewById(R.id.tvUsernameProfile);
        editProfileButton = view.findViewById(R.id.editProfileButton);
        menuOptions = view.findViewById(R.id.menu_options);
        recyclerView = view.findViewById(R.id.recyclerView);

        // Initially hide the menu
        menuOptions.setVisibility(View.GONE);

        // Toggle menu visibility
        burgerButton.setOnClickListener(v -> {
            if (menuOptions.getVisibility() == View.GONE) {
                menuOptions.setVisibility(View.VISIBLE);  // Show menu
            } else {
                menuOptions.setVisibility(View.GONE);  // Hide menu
            }
        });

        account_status_btn = view.findViewById(R.id.account_status);
        change_password_btn = view.findViewById(R.id.change_password);
        logout_btn = view.findViewById(R.id.logout);

        account_status_btn.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), Account_Status.class);
            startActivity(intent);
        });

        change_password_btn.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), Change_Password.class);
            startActivity(intent);
            requireActivity().finish();
        });

        logout_btn.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), SignUpActivity.class);
            startActivity(intent);
            requireActivity().finish();
        });

        // Initialize notes list and adapter
        notesList = new ArrayList<>();
        profileAdapter = new ProfileAdapter(requireContext(), notesList);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(profileAdapter);

        // Edit profile button click
        editProfileButton.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), EditProfile.class);
            startActivity(intent);
        });

        // Fetch username from shared preferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        String username = sharedPreferences.getString("username", null);
        tvUsernameProfile.setText(username);

        // Fetch user_id from shared preferences
        String userId = sharedPreferences.getString("user_id", null);

        // Fetch notes for the user
        if (userId != null) {
            fetchUserNotes(userId);
        } else {
            Toast.makeText(requireContext(), "User ID not found", Toast.LENGTH_SHORT).show();
        }

    }

    private void fetchUserNotes(String userId) {
        String url = "http://10.0.2.2:8000/api/users/" + userId + "/notes"; // Construct the URL

        // Initialize request queue
        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());

        // Create a JsonArrayRequest
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    notesList.clear(); // Clear existing notes
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject noteObject = response.getJSONObject(i);
                            String noteId = noteObject.getString("note_id");
                            String noteContent = noteObject.getString("content");
                            String noteDateCreated = noteObject.getString("created_at");
                            String noteUsername = noteObject.getString("user_name");

                            Note note = new Note(noteUsername, noteDateCreated, noteContent);
                            note.setId(noteId);
                            notesList.add(0, note);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(requireContext(), "Error parsing note", Toast.LENGTH_SHORT).show();
                        }
                    }
                    profileAdapter.notifyDataSetChanged();
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(requireContext(), "Error fetching notes", Toast.LENGTH_SHORT).show();
                });

        // Add request to the queue
        requestQueue.add(jsonArrayRequest);
    }
}
