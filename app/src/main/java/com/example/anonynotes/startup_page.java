package com.example.anonynotes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class startup_page extends AppCompatActivity {
    private Button btnGetStarted;
    private TextView tvWUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_startup_page);

        btnGetStarted = findViewById(R.id.btnGetStarted);
        tvWUsername = findViewById(R.id.tvWUsername);

        // Get the username from Intent extras
        String username = getIntent().getStringExtra("USERNAME");
        if (username != null) {
            tvWUsername.setText(username); // Set the username in the TextView
        }

        btnGetStarted.setOnClickListener(v -> {
            Intent intent = new Intent(startup_page.this, Home_Page.class);
            startActivity(intent);
            finish();
        });
    }
}
