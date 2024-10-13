package com.example.anonynotes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;

public class Write_Page extends AppCompatActivity {

    private EditText write_input;
    private Button submit;
    private ImageButton homeButton;
    private ImageButton profileButton;
    private TextView charCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_page);

        profileButton = findViewById(R.id.ProfileButton);
        homeButton = findViewById(R.id.HomeButton);
        write_input = findViewById(R.id.write_input);
        submit = findViewById(R.id.submit);
        charCount = findViewById(R.id.char_count);


        write_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                charCount.setText(charSequence.length() + "/2000");
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        profileButton.setOnClickListener(v -> {
            Intent intent = new Intent(Write_Page.this, Profile_Page.class);
            startActivity(intent);
            finish();
        });

        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(Write_Page.this, Home_Page.class);
            startActivity(intent);
            finish();
        });

        submit.setOnClickListener(v -> {
            String content = write_input.getText().toString();
            if (!content.isEmpty()) {
                // Retrieve username and userId from SharedPreferences
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                String username = preferences.getString("username", null);
                String userIdString = preferences.getString("user_id", null); // Retrieve user_id as a string
                Log.d("Preferences", "User ID retrieved: " + userIdString);

                if (userIdString != null) { // Check if userId is valid
                    try {
                        int userId = Integer.parseInt(userIdString); // Convert userId to integer
                        sendNoteToServer(username, content, false, userId); // Pass userId as an integer
                        System.out.println("Submitting note with content: " + content); // Log the content being submitted
                    } catch (NumberFormatException e) {
                        Toast.makeText(Write_Page.this, "Invalid User ID format. Please log in again.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Write_Page.this, "User ID not found. Please log in again.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(Write_Page.this, "Please enter some content.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendNoteToServer(String username, String content, boolean anonymous, int userId) {
        // Show loading indicator
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Submitting note...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                OkHttpClient client = new OkHttpClient();
                MediaType JSON = MediaType.get("application/json; charset=utf-8");

                JSONObject json = new JSONObject();
                try {
                    json.put("user_name", username); // Use username from SharedPreferences
                    json.put("content", content);
                    json.put("anonymous", anonymous ? 1 : 0); // Send 1 for true (anonymous) or 0 for false
                    json.put("user_id", userId); // Include user_id in the JSON payload as an integer
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Log the request body
                System.out.println("Request Body: " + json.toString());

                // Retrieve the token from SharedPreferences
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Write_Page.this);
                String token = preferences.getString("auth_token", null);

                RequestBody body = RequestBody.create(json.toString(), JSON);
                Request request = new Request.Builder()
                        .url("http://10.0.2.2:8000/api/notes") // Ensure the URL is correct
                        .post(body)
                        .addHeader("Authorization", "Bearer " + token) // Add the token to the request header
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    return response.body() != null ? response.body().string() : null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                // Dismiss loading dialog
                progressDialog.dismiss();

                // Log server response
                System.out.println("Server response: " + result);

                if (result != null) {
                    // Notify user of successful submission
                    Toast.makeText(Write_Page.this, "Note submitted successfully!", Toast.LENGTH_SHORT).show();

                    // Navigate back to Home_Page
                    Intent intent = new Intent(Write_Page.this, Home_Page.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Notify user of failure
                    Toast.makeText(Write_Page.this, "Failed to submit note. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }
}
