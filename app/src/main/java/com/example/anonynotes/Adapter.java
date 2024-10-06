package com.example.anonynotes;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private List<Note> notes;

    // Constructor
    Adapter(Context context, List<Note> notes) {
        this.layoutInflater = LayoutInflater.from(context);
        this.notes = notes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.custom_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        // Get the current note object and bind data to TextViews
        Note note = notes.get(i);
        viewHolder.tvUsername.setText(note.getUsername());
        viewHolder.dateCreated.setText(note.getDateCreated());
        viewHolder.tvNote.setText(note.getContent());

        String username = note.getUsername();
        if (username.length() > 20) {
            username = username.substring(0, 20) + "...";
        }
        viewHolder.tvUsername.setText(username);

        // Format the created_at timestamp to only show the date
        String fullDateTime = note.getDateCreated(); // This is the full timestamp: "2024-10-04T14:07:58.000000Z"

        // Define the expected date format from the JSON and the target format
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        try {
            // Parse the full timestamp into a Date object
            Date date = inputFormat.parse(fullDateTime);
            // Format the date to just display "yyyy-MM-dd"
            String formattedDate = outputFormat.format(date);
            // Set the formatted date in the dateCreated TextView
            viewHolder.dateCreated.setText(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
            // In case of an error, fallback to showing the full timestamp
            viewHolder.dateCreated.setText(fullDateTime);
        }
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    // This method can be called to update the data from the server
    public void setNotes(List<Note> newNotes) {
        this.notes = newNotes;
        notifyDataSetChanged(); // Notify the adapter that the data has changed
    }

    // ViewHolder class to hold the view references
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvUsername, dateCreated, tvNote;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            dateCreated = itemView.findViewById(R.id.dateCreated);
            tvNote = itemView.findViewById(R.id.tvNote);
        }
    }
}
