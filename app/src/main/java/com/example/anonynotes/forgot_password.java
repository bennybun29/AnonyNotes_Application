package com.example.anonynotes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class forgot_password extends AppCompatActivity {

    private TextView tvGetCodeUsername;
    private TextView tvGetCodeEmail;
    private EditText etUsername;
    private View lineUsername;
    private View lineEmail;
    private TextView backToLogin;
    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        tvGetCodeUsername = findViewById(R.id.tvGetCodeUsername);
        tvGetCodeEmail = findViewById(R.id.tvGetCodeEmail);
        etUsername = findViewById(R.id.etUsername);
        lineUsername = findViewById(R.id.lineUsername);
        lineEmail = findViewById(R.id.lineEmail);
        btnNext = findViewById(R.id.btnNext);
        backToLogin = findViewById(R.id.backToLogIn);

        // Set initial state
        selectTextView(tvGetCodeUsername);

        tvGetCodeUsername.setOnClickListener(v -> selectTextView(tvGetCodeUsername));
        tvGetCodeEmail.setOnClickListener(v -> selectTextView(tvGetCodeEmail));

        backToLogin.setOnClickListener(v -> {
            Intent intent = new Intent(forgot_password.this, LogInActivity.class);
            startActivity(intent);
        });

        btnNext.setOnClickListener(v -> {
            Intent intent = new Intent(forgot_password.this, Confirmation_Code.class);
            startActivity(intent);
        });
    }

    private void selectTextView(TextView selected) {
        if (selected == tvGetCodeUsername) {
            lineUsername.setSelected(true);
            lineEmail.setSelected(false);
            etUsername.setHint("Enter username here");
        } else {
            lineUsername.setSelected(false);
            lineEmail.setSelected(true);
            etUsername.setHint("Enter email here");
        }
    }
}