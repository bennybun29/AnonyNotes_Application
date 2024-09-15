package com.example.anonynotes;

import android.os.Bundle;
import android.view.View;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        tvGetCodeUsername = findViewById(R.id.tvGetCodeUsername);
        tvGetCodeEmail = findViewById(R.id.tvGetCodeEmail);
        etUsername = findViewById(R.id.etUsername);
        lineUsername = findViewById(R.id.lineUsername);
        lineEmail = findViewById(R.id.lineEmail);

        // Set initial state
        selectTextView(tvGetCodeUsername);

        tvGetCodeUsername.setOnClickListener(v -> selectTextView(tvGetCodeUsername));
        tvGetCodeEmail.setOnClickListener(v -> selectTextView(tvGetCodeEmail));
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