package com.example.anonynotes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_page);

        profileButton = findViewById(R.id.ProfileButton);
        homeButton = findViewById(R.id.HomeButton);
        write_input = findViewById(R.id.write_input);
        submit = findViewById(R.id.submit);

        profileButton.setOnClickListener(v -> {
            Intent intent = new Intent(Write_Page.this, Profile_Page.class);
            startActivity(intent);
        });

        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(Write_Page.this, Home_Page.class);
            startActivity(intent);
        });

        submit.setOnClickListener(v -> {
            String content = write_input.getText().toString();
            if (!content.isEmpty()) {
                // Retrieve username from SharedPreferences
                SharedPreferences preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
                String username = preferences.getString("username", null);


                // Pass the username to the method
                sendNoteToServer(username, content, false); // 'false' for anonymous (or 'true' as needed)
                System.out.println("Submitting note with content: " + content); // Log the content being submitted
            } else {
                Toast.makeText(Write_Page.this, "Please enter some content.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendNoteToServer(String username, String content, boolean anonymous) {
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

                // Retrieve the username from SharedPreferences
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Write_Page.this);
                String username = preferences.getString("username", null); // Use the same key

                JSONObject json = new JSONObject();
                try {
                    json.put("user_name", username); // This will now be the correct username
                    json.put("content", content);
                    json.put("anonymous", anonymous ? 1 : 0); // Send 1 for true (anonymous) or 0 for false
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Log the request body
                System.out.println("Request Body: " + json.toString());

                // Retrieve the token from SharedPreferences
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
                } else {
                    // Notify user of failure
                    Toast.makeText(Write_Page.this, "Failed to submit note. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

}
