package org.example.communityapi.auth.dto;

public class LoginResponse {

    private int userId;

    public LoginResponse(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }
}