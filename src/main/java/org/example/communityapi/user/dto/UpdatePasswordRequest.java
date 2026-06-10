package org.example.communityapi.user.dto;

public class UpdatePasswordRequest {

    private String password;
    private String newPassword;

    public UpdatePasswordRequest() {
    }

    public String getPassword() {
        return password;
    }

    public String getNewPassword() {
        return newPassword;
    }
}