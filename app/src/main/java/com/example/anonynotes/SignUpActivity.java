package com.example.anonynotes;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.TextPaint;
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
        // Your PHP script URL
        String url = "http://10.0.2.2/ghostwriter-api/config/signup.php";  // Use 10.0.2.2 for localhost with XAMPP on an Android emulator

        RequestBody formBody = new FormBody.Builder()
                .add("email", email)
                .add("username", username)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Handle the error (e.g., network issues)
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(SignUpActivity.this, "Network Error", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    try {
                        // Parse the JSON response
                        JSONObject jsonResponse = new JSONObject(responseData);
                        String status = jsonResponse.getString("status");
                        String message = jsonResponse.getString("message");

                        runOnUiThread(() -> {
                            if (status.equals("success")) {
                                Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show();
                                // Optionally navigate to another activity

                                // Navigate to the startup page after successful signup
                                Intent intent = new Intent(SignUpActivity.this, startup_page.class); // Replace with your Startup activity
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                        runOnUiThread(() -> Toast.makeText(SignUpActivity.this, "Error parsing response", Toast.LENGTH_SHORT).show());
                    }
                } else {
                    runOnUiThread(() -> Toast.makeText(SignUpActivity.this, "Signup failed: " + response.message(), Toast.LENGTH_SHORT).show());
                }
            }
        });
    }
}