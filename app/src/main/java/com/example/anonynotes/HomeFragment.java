package com.example.anonynotes;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    Adapter adapter;
    ArrayList<Note> notes;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize RecyclerView and Adapter
        recyclerView = view.findViewById(R.id.recyclerView);
        notes = new ArrayList<>();
        adapter = new Adapter(getContext(), notes);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Fetch notes from server
        fetchNotesFromServer();

        return view;
    }

    @SuppressLint("StaticFieldLeak")
    private void fetchNotesFromServer() {
        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... voids) {
                OkHttpClient client = new OkHttpClient();
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                String token = sharedPreferences.getString("auth_token", null);
                Log.d("HomeFragment", "Auth token: " + token); // Ensure this logs the correct token

                Request request = new Request.Builder()
                        .url("http://10.0.2.2:8000/api/notes")
                        .addHeader("Authorization", "Bearer " + token)
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    return response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                Log.d("HomeFragment", "Received result: " + result); // Log the raw response
                if (result != null && !result.isEmpty()) {
                    try {
                        // Attempt to parse the JSON response
                        JSONArray jsonArray = new JSONArray(result);

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String userName = jsonObject.getString("user_name");
                            String content = jsonObject.getString("content");
                            String createdAt = jsonObject.getString("created_at");
                            notes.add(0, new Note(userName, createdAt, content));
                        }

                        // Notify adapter of the updated data
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("HomeFragment", "Error parsing JSON: " + e.getMessage());
                        Toast.makeText(getContext(), "Failed to parse notes", Toast.LENGTH_SHORT).show();

                        // Add an example note in case of a parsing error
                        addExampleNote();
                    }
                } else {
                    // Handle case when result is null or empty
                    Toast.makeText(getContext(), "No notes available", Toast.LENGTH_SHORT).show();

                    // Add an example note when no notes are available
                    addExampleNote();
                }
            }

            // Method to add an example note
            private void addExampleNote() {
                String exampleUserName = "Sample User";
                String exampleContent = "This is an example note content.";
                String exampleCreatedAt = "2024-10-05"; // You can format this as needed

                notes.add(new Note(exampleUserName, exampleCreatedAt, exampleContent));
                adapter.notifyDataSetChanged();
            }

        }.execute();
    }
}
