package org.example.communityapi.post.dto;

public class WriterResponse {

    private int userId;
    private String nickname;
    private String profileImage;

    public WriterResponse(int userId, String nickname, String profileImage) {
        this.userId = userId;
        this.nickname = nickname;
        this.profileImage = profileImage;
    }

    public int getUserId() {
        return userId;
    }

    public String getNickname() {
        return nickname;
    }

    public String getProfileImage() {
        return profileImage;
    }
}