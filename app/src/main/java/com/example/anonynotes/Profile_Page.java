package com.example.anonynotes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.toolbox.JsonArrayRequest;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Profile_Page extends AppCompatActivity {
    private ImageButton Quillpen, HomeButton, burgerButton;
    private Button account_status_btn, change_password_btn, logout_btn, editProfileButton;
    private TextView tvUsernameProfile, tvBioProfile;
    private LinearLayout menuOptions;
    private RecyclerView recyclerView; // RecyclerView to display notes
    private ProfileAdapter profileAdapter; // Adapter for RecyclerView
    private List<Note> notesList; // List to hold notes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_page);

        HomeButton = findViewById(R.id.HomeButton);
        Quillpen = findViewById(R.id.Quillpen);
        burgerButton = findViewById(R.id.burgerButton);
        tvBioProfile = findViewById(R.id.tvBioProfile);
        tvUsernameProfile = findViewById(R.id.tvUsernameProfile);
        editProfileButton = findViewById(R.id.editProfileButton);
        menuOptions = findViewById(R.id.menu_options);
        recyclerView = findViewById(R.id.recyclerView); // Initialize RecyclerView

        // Initially hide the menu
        menuOptions.setVisibility(View.GONE);

        // Burger button click - toggle the visibility of the menu options
        burgerButton.setOnClickListener(v -> {
            if (menuOptions.getVisibility() == View.GONE) {
                menuOptions.setVisibility(View.VISIBLE);  // Show (menu options)
            } else {
                menuOptions.setVisibility(View.GONE);  // Hide (menu options)
            }
        });

        account_status_btn = findViewById(R.id.account_status);
        change_password_btn = findViewById(R.id.change_password);
        logout_btn = findViewById(R.id.logout);

        account_status_btn.setOnClickListener(v -> {
            Intent intent = new Intent(Profile_Page.this, Account_Status.class);
            startActivity(intent);
        });

        change_password_btn.setOnClickListener(v -> {
            Intent intent = new Intent(Profile_Page.this, Change_Password.class);
            startActivity(intent);
            finish();
        });

        logout_btn.setOnClickListener(v -> {
            Intent intent = new Intent(Profile_Page.this, SignUpActivity.class);
            startActivity(intent);
            finish();
        });

        // Initialize notes list and adapter
        notesList = new ArrayList<>();
        profileAdapter = new ProfileAdapter(this, notesList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(profileAdapter);

        // Edit profile button click
        editProfileButton.setOnClickListener(v -> {
            Intent intent = new Intent(Profile_Page.this, EditProfile.class);
            startActivity(intent);
        });

        // Get username from shared preferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String username = sharedPreferences.getString("username", null);
        tvUsernameProfile.setText(username);

        // Fetch user_id from shared preferences
        String userId = sharedPreferences.getString("user_id", null); // Make sure user_id is saved

        // Fetch notes for the user
        if (userId != null) {
            fetchUserNotes(userId);
        } else {
            Toast.makeText(this, "User ID not found", Toast.LENGTH_SHORT).show();
        }

        // Home button click
        HomeButton.setOnClickListener(v -> {
            Intent intent = new Intent(Profile_Page.this, Home_Page.class);
            startActivity(intent);
            finish();
        });

        // Quillpen button click
        Quillpen.setOnClickListener(v -> {
            Intent intent = new Intent(Profile_Page.this, Write_Page.class);
            startActivity(intent);
            finish();
        });

        // Burger button click - toggle the visibility of the menu options
        burgerButton.setOnClickListener(v -> {
            if (menuOptions.getVisibility() == View.GONE) {
                menuOptions.setVisibility(View.VISIBLE);
            } else {
                menuOptions.setVisibility(View.GONE);
            }
        });
    }

    private void fetchUserNotes(String userId) {
        String url = "http://10.0.2.2:8000/api/users/" + userId + "/notes"; // Construct the URL

        // Initialize request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Create a JsonArrayRequest
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray notesArray) {
                        notesList.clear(); // Clear existing notes
                        for (int i = 0; i < notesArray.length(); i++) {
                            try {
                                JSONObject noteObject = notesArray.getJSONObject(i);
                                String noteId = noteObject.getString("note_id"); // Use "note_id"
                                String noteContent = noteObject.getString("content");
                                String noteDateCreated = noteObject.getString("created_at"); // Use "created_at"
                                String noteUsername = noteObject.getString("user_name"); // Use "user_name"

                                Note note = new Note(noteUsername, noteDateCreated, noteContent);
                                note.setId(noteId); // Set ID
                                notesList.add(0, note);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(Profile_Page.this, "Error parsing note", Toast.LENGTH_SHORT).show();
                            }
                        }
                        profileAdapter.notifyDataSetChanged(); // Notify adapter
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(Profile_Page.this, "Error fetching notes", Toast.LENGTH_SHORT).show();
                    }
                });

        // Add request to the queue
        requestQueue.add(jsonArrayRequest);
    }
}
