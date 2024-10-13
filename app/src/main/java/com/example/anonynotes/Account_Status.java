package com.example.anonynotes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Account_Status extends AppCompatActivity {
    private ImageButton btnBackToProfile;
    private TextView tvUsername, accountCreatedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_status);

        btnBackToProfile = findViewById(R.id.btnBackToProfile);
        tvUsername = findViewById(R.id.tvUsername);
        accountCreatedDate = findViewById(R.id.accountCreatedDate);

        btnBackToProfile.setOnClickListener(v -> {
            Intent intent = new Intent(Account_Status.this, Profile_Page.class);
            startActivity(intent);
            finish();
        });

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String username = sharedPreferences.getString("username", null);
        String accountCreated = sharedPreferences.getString("created_at", null);
        tvUsername.setText(username);

        // Format the created_at timestamp to only show the date
        String fullDateTime = accountCreated; // This is the full timestamp: "2024-10-04T14:07:58.000000Z"

        // Define the expected date format from the JSON and the target format
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        try {
            // Parse the full timestamp into a Date object
            Date date = inputFormat.parse(fullDateTime);
            // Format the date to just display "yyyy-MM-dd"
            String formattedDate = outputFormat.format(date);
            // Set the formatted date in the dateCreated TextView
            accountCreatedDate.setText(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
            // In case of an error, fallback to showing the full timestamp
            accountCreatedDate.setText(fullDateTime);
        }
    }
}
