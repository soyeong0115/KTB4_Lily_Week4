package org.example.communityapi.user.dto;

public class ProfileResponse {

    private String email;
    private String nickname;
    private String profileImage;

    public ProfileResponse(String email, String nickname, String profileImage) {
        this.email = email;
        this.nickname = nickname;
        this.profileImage = profileImage;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

    public String getProfileImage() {
        return profileImage;
    }
}