package com.example.anonynotes;

import java.util.List;

public class Comment {
    private String name;
    private String dateCreated;
    private String content;
    private List<Comment> replies;

    public Comment(String name, String dateCreated, String content, List<Comment> replies) {
        this.name = name;
        this.dateCreated = dateCreated;
        this.replies = replies;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public List<Comment> getReplies() {
        return replies;
    }

    public void setReplies(List<Comment> replies) {
        this.replies = replies;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
