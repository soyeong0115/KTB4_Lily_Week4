package org.example.communityapi.auth.dto;

public class SignupRequest {

    private String email;
    private String password;
    private String nickname;
    private String profileImage;

    public SignupRequest(
            String email,
            String password,
            String nickname,
            String profileImage
    ) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.profileImage = profileImage;
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