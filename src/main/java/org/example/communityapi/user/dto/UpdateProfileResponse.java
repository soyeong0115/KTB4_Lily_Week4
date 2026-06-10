package org.example.communityapi.user.dto;

public class UpdateProfileResponse {

    private String nickname;
    private String profileImage;

    public UpdateProfileResponse(String nickname, String profileImage) {
        this.nickname = nickname;
        this.profileImage = profileImage;
    }

    public String getNickname() {
        return nickname;
    }

    public String getProfileImage() {
        return profileImage;
    }
}