package com.example.anonynotes;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class EditProfile extends AppCompatActivity {

    private ImageButton btnBackToProfilePage;
    private EditText editEmailAddress, editUsername, editBio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_editprofile);

        btnBackToProfilePage = findViewById(R.id.btnBackToEditProfile);
        editEmailAddress = findViewById(R.id.etBio);
        editBio = findViewById(R.id.editBio);
        editUsername = findViewById(R.id.editUsername);

        btnBackToProfilePage.setOnClickListener(v -> {
            Intent intent = new Intent(EditProfile.this, Profile_Page.class);
            startActivity(intent);
            finish();
        });

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
