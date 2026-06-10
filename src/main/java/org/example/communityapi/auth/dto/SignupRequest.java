package org.example.communityapi.auth.dto;

public class SignupRequest {

    private String email;
    private String password;
    private String nickname;
    private String profileImage;

    public SignupRequest() {
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public String getProfileImage() {
        return profileImage;
    }
}