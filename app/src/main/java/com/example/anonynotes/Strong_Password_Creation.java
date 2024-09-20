package com.example.anonynotes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Strong_Password_Creation extends AppCompatActivity {

    private TextView backToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_strong_password_creation);

        backToLogin = findViewById(R.id.backToLogIn);

        backToLogin.setOnClickListener(v -> {
            Intent intent = new Intent(Strong_Password_Creation.this, LogInActivity.class);
            startActivity(intent);
        });
    }
}
