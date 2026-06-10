package org.example.communityapi.auth.dto;

public class SignupResponse {

    private int userId;

    public SignupResponse(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }
}