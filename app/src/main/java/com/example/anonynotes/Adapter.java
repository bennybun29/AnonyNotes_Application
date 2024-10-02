package com.example.anonynotes;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private List<Note> notes; // Change to List of Note objects

    Adapter(Context context, List<Note> notes) {
        this.layoutInflater = LayoutInflater.from(context);
        this.notes = notes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.custom_view, viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        // Bind the data to the TextViews
        Note note = notes.get(i);
        viewHolder.tvUsername.setText(note.getUsername());
        viewHolder.dateCreated.setText(note.getDateCreated());
        viewHolder.tvNote.setText(note.getNote());
    }

    @Override
    public int getItemCount() {
        return notes.size(); // Use size of notes list
    }

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
