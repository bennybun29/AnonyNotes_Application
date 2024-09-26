package com.example.anonynotes;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class LogInActivity extends AppCompatActivity {
    private EditText ETEmailUsername, ETPassword;
    private Button LogInBTN;
    private OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);

        ETEmailUsername = findViewById(R.id.ETEmailAddress);
        ETPassword = findViewById(R.id.ETPassword);
        LogInBTN = findViewById(R.id.LogInBTN);

        LogInBTN.setOnClickListener(v -> {
            String emailUsername = ETEmailUsername.getText().toString().trim();
            String password = ETPassword.getText().toString().trim();

            if (TextUtils.isEmpty(emailUsername) || TextUtils.isEmpty(password)) {
                Toast.makeText(LogInActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Send login data to the server
            sendLoginData(emailUsername, password);
        });
    }

    private void sendLoginData(String emailUsername, String password) {
        String url = "http://10.0.2.2/ghostwriter-api/config/login.php";

        RequestBody formBody = new FormBody.Builder()
                .add("email_username", emailUsername)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(LogInActivity.this, "Network Error", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    try {
                        JSONObject jsonResponse = new JSONObject(responseData);
                        String status = jsonResponse.getString("status");
                        String message = jsonResponse.getString("message");

                        runOnUiThread(() -> {
                            if (status.equals("success")) {
                                Toast.makeText(LogInActivity.this, message, Toast.LENGTH_SHORT).show();
                                // Redirect to Home Page
                                Intent intent = new Intent(LogInActivity.this, Home_Page.class);
                                // Clear the back stack so the user cannot go back to the login or sign-up screens
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(LogInActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                        runOnUiThread(() -> Toast.makeText(LogInActivity.this, "Error parsing response", Toast.LENGTH_SHORT).show());
                    }
                } else {
                    runOnUiThread(() -> Toast.makeText(LogInActivity.this, "Login failed: " + response.message(), Toast.LENGTH_SHORT).show());
                }
            }
        });
    }
}
