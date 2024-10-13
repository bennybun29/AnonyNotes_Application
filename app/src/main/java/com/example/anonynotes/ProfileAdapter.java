package com.example.anonynotes;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private List<Note> notes;

    // Constructor
    ProfileAdapter(Context context, List<Note> notes) {
        this.layoutInflater = LayoutInflater.from(context);
        this.notes = notes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // Inflate the custom layout for profile notes
        View view = layoutInflater.inflate(R.layout.custom_view_profile_notes, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        // Get the current note object and bind data to TextViews
        Note note = notes.get(i);
        viewHolder.tvUsername.setText(note.getUsername());
        viewHolder.tvNote.setText(note.getContent());

        // Format and set the date created
        String fullDateTime = note.getDateCreated(); // Example: "2024-10-04T14:07:58.000000Z"
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        try {
            Date date = inputFormat.parse(fullDateTime);
            String formattedDate = outputFormat.format(date);
            viewHolder.dateCreated.setText(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
            viewHolder.dateCreated.setText(fullDateTime); // Fallback
        }

        // Set the username with a maximum length of 20 characters
        String username = note.getUsername();
        if (username.length() > 20) {
            username = username.substring(0, 20) + "...";
        }
        viewHolder.tvUsername.setText(username);

        // Add click listener for the comment button
        viewHolder.commentButton.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), CommentSection.class);
            intent.putExtra("note_id", note.getId()); // Assuming `note` has an ID field
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    // Method to update the data from the server
    public void setNotes(List<Note> newNotes) {
        this.notes = newNotes;
        notifyDataSetChanged(); // Notify the adapter that the data has changed
    }

    // ViewHolder class to hold the view references
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvUsername, dateCreated, tvNote;
        private ImageButton commentButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            dateCreated = itemView.findViewById(R.id.dateCreated);
            tvNote = itemView.findViewById(R.id.tvNote);
            commentButton = itemView.findViewById(R.id.commentButton);
        }
    }
}
