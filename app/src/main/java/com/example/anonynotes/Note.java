package com.example.anonynotes;

public class Note {
    private String username, dateCreated, content;

    public Note(String username, String dateCreated, String content) {
        this.username = username;
        this.dateCreated = dateCreated;
        this.content = content;
    }

    // Getters
    public String getUsername() { return username; }
    public String getDateCreated() { return dateCreated; }
    public String getContent() { return content; }
}
