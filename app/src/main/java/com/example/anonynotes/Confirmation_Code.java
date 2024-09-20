package com.example.anonynotes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;



public class Confirmation_Code extends AppCompatActivity {
    private TextView backToLogin;
    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_code);

        backToLogin = findViewById(R.id.backToLogIn);
        btnNext = findViewById(R.id.btnNext);

        btnNext.setOnClickListener(v -> {
            Intent intent = new Intent(Confirmation_Code.this, Strong_Password_Creation.class);
            startActivity(intent);
        });

        backToLogin.setOnClickListener(v -> {
            Intent intent = new Intent(Confirmation_Code.this, LogInActivity.class);
            startActivity(intent);
        });

    }
}