package com.example.anonynotes;

public class SignUpResponse {
    private String status;
    private String message;
    private String token; // Include this if your API returns a token

    // Getters
    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }
}
