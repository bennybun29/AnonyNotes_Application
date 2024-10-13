package com.example.anonynotes;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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

        // Setup clickable "Sign Up" text
        TextView textView = findViewById(R.id.tvSignUp);
        String text = "Don't have an account? Sign Up";

        SpannableString spannableString = new SpannableString(text);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                // Redirect to SignUpActivity
                Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
                ds.setColor(getResources().getColor(android.R.color.black));
            }
        };

        spannableString.setSpan(clickableSpan, 23, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        // Setup clickable "Forgot Password?" text
        TextView forgotPasswordTV = findViewById(R.id.tvForgotPassword);
        String forgotPasswordText = "Forgot Password?";
        SpannableString spannableForgotPassword = new SpannableString(forgotPasswordText);

        ClickableSpan clickableForgotPassword = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                // Redirect to ForgotPasswordActivity
                Intent intent = new Intent(LogInActivity.this, forgot_password.class);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
                ds.setColor(getResources().getColor(android.R.color.black));
            }
        };

        spannableForgotPassword.setSpan(clickableForgotPassword, 0, forgotPasswordText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        forgotPasswordTV.setText(spannableForgotPassword);
        forgotPasswordTV.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void saveEmail(String email) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("email", email);
        editor.apply();
    }

    private void saveUsername(String username) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("username", username); // Store the username
        editor.apply();
    }

    private void saveAuthToken(String token) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("auth_token", token);
        editor.apply();
    }

    private void saveUserId(String userId) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user_id", userId); // Store the user ID
        editor.apply();
        Log.d("Preferences", "User ID saved: " + userId); // Debug log
    }

    private void saveAccountCreatedDate(String created_at) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("created_at", created_at); // Store the user ID
        editor.apply();
    }

    private void sendLoginData(String emailUsername, String password) {
        String url = "http://10.0.2.2:8000/api/login";  // Make sure this URL is correct

        RequestBody formBody = new FormBody.Builder()
                .add("email", emailUsername)  // Change this key to match your backend API
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
                        String message = jsonResponse.getString("message");
                        String token = jsonResponse.optString("token", null); // Get token if available
                        String username = jsonResponse.optString("username", null);
                        String userId = jsonResponse.optString("user_id", null); // Get user ID from response
                        String created_at = jsonResponse.optString("created_at", null);

                        runOnUiThread(() -> {
                            Toast.makeText(LogInActivity.this, message, Toast.LENGTH_SHORT).show();
                            if (token != null) {
                                // Redirect to Home Page and store token, username, and user ID if needed
                                saveAuthToken(token);
                                saveUsername(username);
                                saveEmail(emailUsername);
                                saveAccountCreatedDate(created_at);
                                if (userId != null) {
                                    saveUserId(userId); // Save user ID
                                }
                                Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // This clears the back stack
                                startActivity(intent);
                                finish();
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                        runOnUiThread(() -> Toast.makeText(LogInActivity.this, "Error parsing response", Toast.LENGTH_SHORT).show());
                    }
                } else {
                    runOnUiThread(() -> {
                        try {
                            String errorResponse = response.body().string();
                            JSONObject jsonError = new JSONObject(errorResponse);
                            String errorMessage = jsonError.optString("error", "Login failed"); // Adjust based on your error structure
                            Toast.makeText(LogInActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LogInActivity.this, "Login failed: " + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
