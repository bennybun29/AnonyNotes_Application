package com.example.anonynotes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Profile_Page extends AppCompatActivity {
    private ImageButton Quillpen;
    private ImageButton HomeButton;
    private ImageButton SignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_page);{

            HomeButton = findViewById(R.id.HomeButton);
            Quillpen = findViewById(R.id.Quillpen);
            SignOut = findViewById(R.id.SignOutButton);

            SignOut.setOnClickListener(v -> {
                Intent intent = new Intent(Profile_Page.this, EditProfile.class);
                startActivity(intent);
                finish();
            });

            HomeButton.setOnClickListener(v -> {
                Intent intent = new Intent(Profile_Page.this, Home_Page.class);
                startActivity(intent);
            });

            Quillpen.setOnClickListener(v -> {
                Intent intent = new Intent(Profile_Page.this, Write_Page.class);
                startActivity(intent);
            });

        }
    }
}
