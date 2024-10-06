package com.example.anonynotes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Profile_Page extends AppCompatActivity {
    private ImageButton Quillpen, HomeButton, burgerButton;
    private Button editProfileButton;
    private TextView tvUsernameProfile, tvBioProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_page);{

            HomeButton = findViewById(R.id.HomeButton);
            Quillpen = findViewById(R.id.Quillpen);
            burgerButton = findViewById(R.id.burgerButton);
            tvBioProfile = findViewById(R.id.tvBioProfile);
            tvUsernameProfile = findViewById(R.id.tvUsernameProfile);
            editProfileButton = findViewById(R.id.editProfileButton);

            editProfileButton.setOnClickListener(v -> {
                Intent intent = new Intent(Profile_Page.this, EditProfile.class);
                startActivity(intent);
            });

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String username = sharedPreferences.getString("username", null);

            tvUsernameProfile.setText(username);

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
