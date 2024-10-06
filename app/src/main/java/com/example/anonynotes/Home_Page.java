package com.example.anonynotes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;

public class Home_Page extends AppCompatActivity {
    private ImageButton Quillpen, ProfileButton;
    RecyclerView recyclerView;
    Adapter adapter;
    ArrayList<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        recyclerView = findViewById(R.id.recyclerView);

        // Initialize notes list and adapter
        notes = new ArrayList<>();
        adapter = new Adapter(this, notes);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Fetch notes from server on load
        fetchNotesFromServer();

        // Set up navigation buttons
        Quillpen = findViewById(R.id.Quillpen);
        ProfileButton = findViewById(R.id.ProfileButton);

        Quillpen.setOnClickListener(v -> {
            Intent intent = new Intent(Home_Page.this, Write_Page.class);
            startActivity(intent);
        });

        ProfileButton.setOnClickListener(v -> {
            Intent intent = new Intent(Home_Page.this, Profile_Page.class);
            startActivity(intent);
        });
    }

    @SuppressLint("StaticFieldLeak")
    private void fetchNotesFromServer() {
        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... voids) {
                OkHttpClient client = new OkHttpClient();
                // Use the correct SharedPreferences
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String token = sharedPreferences.getString("auth_token", null);
                Log.d("Home_Page", "Auth token: " + token); // Ensure this logs the correct token

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
                Log.d("Home_Page", "Received result: " + result); // Log the raw response
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
                        Log.e("Home_Page", "Error parsing JSON: " + e.getMessage());
                        Toast.makeText(Home_Page.this, "Failed to parse notes", Toast.LENGTH_SHORT).show();

                        // Add an example note in case of a parsing error
                        addExampleNote();
                    }
                } else {
                    // Handle case when result is null or empty
                    Toast.makeText(Home_Page.this, "No notes available", Toast.LENGTH_SHORT).show();

                    // Add an example note when no notes are available
                    addExampleNote();
                }
            }

            // Method to add an example note
            private void addExampleNote() {
                // Create a sample note
                String exampleUserName = "Sample User";
                String exampleContent = "This is an example note content.";
                String exampleCreatedAt = "2024-10-05"; // You can format this as needed

                // Add the example note to the list
                notes.add(new Note(exampleUserName, exampleCreatedAt, exampleContent));

                // Notify the adapter of the change
                adapter.notifyDataSetChanged();
            }

        }.execute();
    }
}
