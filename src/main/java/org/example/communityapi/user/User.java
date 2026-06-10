package org.example.communityapi.user;

public class User {

    private int userId;
    private String email;
    private String password;
    private String nickname;
    private String profileImage;

    public User(int userId, String email, String password, String nickname, String profileImage) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.profileImage = profileImage;
    }

    public int getUserId() {
        return userId;
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