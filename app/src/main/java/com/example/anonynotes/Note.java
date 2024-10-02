package com.example.anonynotes;

public class Note {
    private String username;
    private String dateCreated;
    private String note;

    public Note(String username, String dateCreated, String note) {
        this.username = username;
        this.dateCreated = dateCreated;
        this.note = note;
    }

    public String getUsername() {
        return username;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public String getNote() {
        return note;
    }
}
