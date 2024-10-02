package com.example.anonynotes;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

        // Setup clickable "Sign Up" text
        TextView textView = findViewById(R.id.tvSignUp); // Ensure the correct ID
        String text = "Don't have an account? Sign Up";

        SpannableString spannableString = new SpannableString(text);

        // Make "Sign Up" clickable
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
                ds.setUnderlineText(false); // Underline "Sign Up"
                ds.setColor(getResources().getColor(android.R.color.black)); // Set color of "Sign Up"
            }
        };

        // Set the clickable span from "Sign Up" position to the end
        spannableString.setSpan(clickableSpan, 23, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Apply the spannable string to the TextView and enable clickable text
        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        // Setup clickable "Forgot Password?" text
        TextView forgotPasswordTV = findViewById(R.id.tvForgotPassword);
        String forgotPasswordText = "Forgot Password?";

        // Create a SpannableString for the Forgot Password text
        SpannableString spannableForgotPassword = new SpannableString(forgotPasswordText);

        // Make "Forgot Password?" clickable
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
                ds.setUnderlineText(false); // Underline the text
                ds.setColor(getResources().getColor(android.R.color.black)); // Set color for "Forgot Password?"
            }
        };

        // Apply the clickable span to the entire "Forgot Password?" text
        spannableForgotPassword.setSpan(clickableForgotPassword, 0, forgotPasswordText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Set the clickable text and enable clickable movement
        forgotPasswordTV.setText(spannableForgotPassword);
        forgotPasswordTV.setMovementMethod(LinkMovementMethod.getInstance());
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
