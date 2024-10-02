package com.example.anonynotes;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class EditProfile_Bio extends AppCompatActivity {
    private ImageButton btnBackToEditProfile, btnConfirmChanges;
    private EditText etBio;
    private TextView character_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_editprofile_bio);

        btnBackToEditProfile = findViewById(R.id.btnBackToEditProfile);
        btnConfirmChanges = findViewById(R.id.btnConfirmChanges);
        character_count = findViewById(R.id.character_count);
        etBio = findViewById(R.id.etBio);

        btnBackToEditProfile.setOnClickListener(v -> showUnsavedChangesDialog());

        // Character Counter
        etBio.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // No action needed here
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Update character count dynamically
                int length = charSequence.length();
                character_count.setText(length + "/50");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // No action needed here
            }
        });
    }

    @Override
    public void onBackPressed() {
        showUnsavedChangesDialog(); // Show the dialog without calling super
    }

    private void showUnsavedChangesDialog() {
        new AlertDialog.Builder(EditProfile_Bio.this)
                .setTitle("Unsaved Changes")
                .setMessage("Are you sure you want to leave? Unsaved changes will be lost.")
                .setPositiveButton("Yes", (dialog, which) -> {
                    // User confirmed to leave, navigate to the EditProfile activity
                    Intent intent = new Intent(EditProfile_Bio.this, EditProfile.class);
                    startActivity(intent);
                    finish(); // Optionally finish the current activity
                })
                .setNegativeButton("No", (dialog, which) -> {
                    // User canceled, dismiss the dialog
                    dialog.dismiss();
                })
                .show();
    }
}
