package org.example.communityapi.user.dto;

public class ProfileResponse {

    private int userId;
    private String email;
    private String nickname;
    private String profileImage;

    public ProfileResponse(int userId, String email, String nickname, String profileImage) {
        this.userId = userId;
        this.email = email;
        this.nickname = nickname;
        this.profileImage = profileImage;
    }

    public int getUserId() {
        return userId;
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