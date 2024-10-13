package com.example.anonynotes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.*;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class SignUpActivity extends AppCompatActivity {

    private TextInputEditText ETEmailAddress, ETUsername, ETPassword, ETConfirmPassword;
    private MaterialButton SignUpBTN;
    private OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.sign_up);

        //Layouts
        ETEmailAddress = findViewById(R.id.ETEmailAddress);
        ETUsername = findViewById(R.id.ETUsername);
        ETPassword = findViewById(R.id.ETPassword);
        ETConfirmPassword = findViewById(R.id.ETConfirmPassword);
        SignUpBTN = findViewById(R.id.SignUpBTN);

        SignUpBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ETEmailAddress.getText().toString().trim();
                String username = ETUsername.getText().toString().trim();
                String password = ETPassword.getText().toString().trim();
                String confirmPassword = ETConfirmPassword.getText().toString().trim();

                if (email.isEmpty() || username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.equals(confirmPassword)) {
                    sendSignUpData(email, username, password);
                } else {
                    Toast.makeText(SignUpActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
            }
        });

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        // Set up the "Already have an account? Sign In" clickable text
        TextView textView = findViewById(R.id.tvLogIn);
        String text = "Already have an account? Log In";

        SpannableString spannableString = new SpannableString(text);

        // Make "Sign In" clickable
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                // Redirect to SignInActivity
                Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true); // Add underline to the "Sign In"
                ds.setColor(getResources().getColor(android.R.color.black)); // Set color of "Sign In"
            }
        };

        // Set the clickable span from "Sign In" position to the end
        spannableString.setSpan(clickableSpan, 25, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    // Method to send signup data to the server
    private void sendSignUpData(String email, String username, String password) {
        // Your API URL
        String url = "http://10.0.2.2:8000/api/register";  // Use 10.0.2.2 for localhost with XAMPP on an Android emulator

        // Create a JSON object with the required fields
        JSONObject json = new JSONObject();
        try {
            json.put("email", email);
            json.put("user_name", username);  // Ensure this matches what your API expects
            json.put("password", password);
            json.put("password_confirmation", password);  // If you have a separate confirmation field, use it here
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Create a request body with the JSON content
        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(json.toString(), JSON);

        // Build the request
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        // Send the request asynchronously
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Handle the error (e.g., network issues)
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(SignUpActivity.this, "Network Error", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string(); // Always read the response body first
                if (response.isSuccessful()) {
                    try {
                        // Parse the JSON response for a successful signup
                        JSONObject jsonResponse = new JSONObject(responseData);
                        String message = jsonResponse.getString("message");
                        String token = jsonResponse.getString("token");  // Extract token from the response
                        String userId = jsonResponse.optString("user_id", null);
                        String created_at = jsonResponse.optString("created_at", null);

                        runOnUiThread(() -> {
                            Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show();
                            // Navigate to the startup page after successful signup
                            saveUsername(username);
                            saveAuthToken(token);
                            saveEmail(email);
                            saveAccountCreatedDate(created_at);
                            if (userId != null) {
                                saveUserId(userId); // Save user ID
                            }
                            Intent intent = new Intent(SignUpActivity.this, startup_page.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // This clears the back stack
                            startActivity(intent);
                            finish();

                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                        runOnUiThread(() -> Toast.makeText(SignUpActivity.this, "Error parsing response", Toast.LENGTH_SHORT).show());
                    }
                } else {
                    // Handle specific error responses
                    String errorMessage = "Signup failed. Please try again.";

                    try {
                        // Parse the error response to find a message
                        JSONObject errorJson = new JSONObject(responseData);
                        if (errorJson.has("message")) {
                            errorMessage = errorJson.getString("message");
                        } else if (errorJson.has("errors")) {
                            JSONObject errors = errorJson.getJSONObject("errors");
                            if (errors.has("user_name")) {
                                errorMessage = "Account/Username already taken"; // Customize this message
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    // Log the error message for debugging
                    Log.e("SignUpActivity", "Error response: " + responseData);

                    String finalErrorMessage = errorMessage;
                    runOnUiThread(() -> Toast.makeText(SignUpActivity.this, finalErrorMessage, Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    // Method to save the username
    private void saveUsername(String username) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("username", username);
        editor.apply();
    }

    private void saveEmail(String email) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("email", email);
        editor.apply();
    }

    private void saveUserId(String userId) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user_id", userId); // Store the user ID
        editor.apply();
    }

    private void saveAccountCreatedDate(String created_at) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("created_at", created_at); // Store the user ID
        editor.apply();
    }

    // Method to save the authentication token
    private void saveAuthToken(String token) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("auth_token", token);
        editor.apply();
    }
}
