package com.example.anonynotes;

public class ProfileNotes {

    private String username, dateCreated, content, id;

    public ProfileNotes(String username, String dateCreated, String content) {
        this.username = username;
        this.dateCreated = dateCreated;
        this.content = content;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsername() { return username; }
    public String getDateCreated() { return dateCreated; }
    public String getContent() { return content; }
}
