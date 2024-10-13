package com.example.anonynotes;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class EditProfile extends AppCompatActivity {

    private ImageButton btnBackToProfilePage;
    private EditText etEmail, editUsername, editBio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_editprofile);

        btnBackToProfilePage = findViewById(R.id.btnBackToEditProfile);
        etEmail = findViewById(R.id.etEmail);
        editBio = findViewById(R.id.editBio);
        editUsername = findViewById(R.id.editUsername);

        btnBackToProfilePage.setOnClickListener(v -> {

            ProfileFragment profileFragment = new ProfileFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, profileFragment) // Replace with your fragment container ID
                    .addToBackStack(null) // Optional: Add to back stack to allow back navigation
                    .commit();
        });


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String username = sharedPreferences.getString("username", null);
        String email = sharedPreferences.getString("email", null);

        etEmail.setText(email);
        editUsername.setText(username);

        editUsername.setOnClickListener(v -> {
            Intent intent = new Intent(EditProfile.this, EditProfile_Username.class);
            startActivity(intent);
            finish();
        });

        editBio.setOnClickListener(v -> {
            Intent intent = new Intent(EditProfile.this, EditProfile_Bio.class);
            startActivity(intent);
            finish();
        });
    }
}
