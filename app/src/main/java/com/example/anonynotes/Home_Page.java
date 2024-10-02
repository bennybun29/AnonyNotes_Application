package com.example.anonynotes;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Home_Page extends AppCompatActivity {
    private ImageButton Quillpen;
    private  ImageButton ProfileButton;
    RecyclerView recyclerView;
    Adapter adapter;
    ArrayList<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);{

            recyclerView = findViewById(R.id.recyclerView);


            // Manually adding some notes
            notes = new ArrayList<>();
            notes.add(new Note("User1", "2024-09-29", "This is the first note."));
            notes.add(new Note("User2", "2024-09-28", "Here is another note."));
            notes.add(new Note("User3", "2024-09-27", "Third note with more content. Third note with more content. Third note with more content."));
            notes.add(new Note("User1", "2024-09-29", "This is the first note."));
            notes.add(new Note("User2", "2024-09-28", "Here is another note."));
            notes.add(new Note("User3", "2024-09-27", "Third note with more content. Third note with more content. Third note with more content."));
            notes.add(new Note("User1", "2024-09-29", "This is the first note."));
            notes.add(new Note("User2", "2024-09-28", "Here is another note."));
            notes.add(new Note("User3", "2024-09-27", "Third note with more content. Third note with more content. Third note with more content."));

            // Set up the adapter with the notes
            adapter = new Adapter(this, notes);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
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

