package org.example.communityapi.user.dto;

public class NicknameCheckResponse {

    private boolean available;

    public NicknameCheckResponse(boolean available) {
        this.available = available;
    }

    public boolean isAvailable() {
        return available;
    }
}