package com.example.anonynotes;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Write_Page extends AppCompatActivity {

    private ImageButton HomeButton;
    private ImageButton ProfileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_write_page);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        HomeButton = findViewById(R.id.HomeButton);
        ProfileButton = findViewById(R.id.ProfileButton);

        ProfileButton.setOnClickListener(v -> {
            Intent intent = new Intent(Write_Page.this, Profile_Page.class);
            startActivity(intent);
        });
        HomeButton.setOnClickListener(v -> {
            Intent intent = new Intent(Write_Page.this, Home_Page.class);
            startActivity(intent);
        });

        EditText writeInput = findViewById(R.id.write_input);
        TextView charCount = findViewById(R.id.char_count);

        writeInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                charCount.setText(s.length() + "/2000");
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
}
