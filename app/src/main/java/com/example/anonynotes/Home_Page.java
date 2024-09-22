package com.example.anonynotes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Home_Page extends AppCompatActivity {
    private ImageButton Quillpen;
    private  ImageButton ProfileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);{

            Quillpen = findViewById(R.id.Quillpen);
            ProfileButton = findViewById(R.id.ProfileButton);

            Quillpen.setOnClickListener(v -> {
                Intent intent = new Intent(Home_Page.this, Write_Page.class);
                startActivity(intent);
            });

            ProfileButton.setOnClickListener(v -> {
                Intent intent = new Intent(Home_Page.this, Profile_Page.class);
                startActivity(intent);
            });
        }
    }
}

