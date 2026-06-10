package org.example.communityapi.user.dto;

public class UpdateProfileRequest {

    private String nickname;
    private String profileImage;

    public UpdateProfileRequest() {
    }

    public String getNickname() {
        return nickname;
    }

    public String getProfileImage() {
        return profileImage;
    }
}