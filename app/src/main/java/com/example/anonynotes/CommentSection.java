package com.example.anonynotes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CommentSection extends AppCompatActivity {
    private List<Comment> commentArrayList;
    private EditText etComment;
    private ImageButton btnSend;
    private ImageButton btnBackArrow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_comment_section);

        btnBackArrow = findViewById(R.id.btnBackArrow);
        btnBackArrow.setOnClickListener(v -> {
            Intent intent = new Intent(CommentSection.this, Home_Page.class);
            startActivity(intent);
        });

        commentArrayList = generateComments();

        // Setup RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvViewComments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CommentAdapter commentAdapter = new CommentAdapter((ArrayList<Comment>) commentArrayList, this);
        recyclerView.setAdapter(commentAdapter);

        setupListeners(commentArrayList, commentAdapter);
    }

    private List<Comment> generateComments() {
        // Initialize views
        etComment = findViewById(R.id.etComment);
        btnSend = findViewById(R.id.btnSend);

        // List to store main comments
        List<Comment> comments = new ArrayList<>();

        // Create replies for the first comment
        List<Comment> repliesForComment1 = new ArrayList<>();
        repliesForComment1.add(new Comment("User A", "10/10/10", "one", new ArrayList<>()));

        // Add the main comment with its replies
        comments.add(new Comment("User 1", "10/10/10", "I love macaroni", repliesForComment1));

        return comments;
    }

    private void setupListeners(List<Comment> comments, RecyclerView.Adapter adapter) {
        btnSend.setOnClickListener(v -> addReply(comments, adapter));
    }

    // Helper function to add the reply
    @SuppressLint("NotifyDataSetChanged")
    private void addReply(List<Comment> comments, RecyclerView.Adapter adapter) {
        String replyText = etComment.getText().toString();
        if (!replyText.isEmpty()) {
            // Assuming you're replying to the first comment (change position as needed)
            Comment mainComment = comments.get(0);

            // Get the current date and time
            String currentDateTime = getCurrentDateTime();

            // Add the reply to the first comment's replies list
            mainComment.getReplies().add(new Comment("Abad", currentDateTime, replyText, new ArrayList<>()));

            // Clear the EditText field after the reply is added
            etComment.getText().clear();

            // Notify the adapter that the data has changed
            adapter.notifyDataSetChanged();
        }
    }

    // Method to get the current date and time in a specific format
    @SuppressLint("DefaultLocale")
    private String getCurrentDateTime() {
        Calendar calendar = Calendar.getInstance();
        return String.format("%02d/%02d/%04d",
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.YEAR));
    }
}
