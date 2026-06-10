package org.example.communityapi.user;

import org.example.communityapi.user.dto.NicknameCheckResponse;
import org.example.communityapi.user.dto.UpdatePasswordRequest;
import org.example.communityapi.user.dto.UpdateProfileRequest;
import org.example.communityapi.user.dto.UpdateProfileResponse;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    // UserRepository 주입하기
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 프로필 수정
    public UpdateProfileResponse updateProfile(Integer userId, UpdateProfileRequest request) {
        if (userId == null) {
            throw new IllegalArgumentException("unauthorized");
        }

        User user = userRepository.findById(userId);

        if (user == null) {
            throw new IllegalArgumentException("unauthorized");
        }

        if (request.getNickname() == null && request.getProfileImage() == null) {
            throw new IllegalArgumentException("invalid_request");
        }

        if (request.getNickname() != null) {
            checkNickname(request.getNickname());

            if (!user.getNickname().equals(request.getNickname())
                    && userRepository.existsByNickname(request.getNickname())) {
                throw new IllegalArgumentException("nickname_duplicated");
            }
        }

        user.updateProfile(request.getNickname(), request.getProfileImage());

        return new UpdateProfileResponse(user.getNickname(), user.getProfileImage());
    }

    // 닉네임 중복 확인
    public NicknameCheckResponse checkNicknameDuplicated(String nickname) {
        if (nickname == null || nickname.isBlank()) {
            throw new IllegalArgumentException("invalid_request");
        }

        checkNickname(nickname);

        if (userRepository.existsByNickname(nickname)) {
            return new NicknameCheckResponse(false);
        }

        return new NicknameCheckResponse(true);
    }

    // 비밀번호 수정
    public void updatePassword(Integer userId, UpdatePasswordRequest request) {
        if (userId == null) {
            throw new IllegalArgumentException("unauthorized");
        }

        User user = userRepository.findById(userId);

        if (user == null) {
            throw new IllegalArgumentException("unauthorized");
        }

        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new IllegalArgumentException("invalid_request");
        }

        if (request.getNewPassword() == null || request.getNewPassword().isBlank()) {
            throw new IllegalArgumentException("invalid_request");
        }

        if (!user.getPassword().equals(request.getPassword())) {
            throw new IllegalArgumentException("unauthorized");
        }

        if (!checkPasswordFormat(request.getNewPassword())) {
            throw new IllegalArgumentException("invalid_request");
        }

        user.updatePassword(request.getNewPassword());
    }

    // 회원 탈퇴
    public void deleteUser(Integer userId) {
        if (userId == null) {
            throw new IllegalArgumentException("unauthorized");
        }

        User user = userRepository.findById(userId);

        if (user == null) {
            throw new IllegalArgumentException("unauthorized");
        }

        userRepository.delete(userId);
    }

    // 닉네임 형식 검사
    private void checkNickname(String nickname) {
        if (nickname.isBlank()) {
            throw new IllegalArgumentException("invalid_request");
        }

        if (nickname.contains(" ")) {
            throw new IllegalArgumentException("invalid_request");
        }

        if (nickname.length() > 10) {
            throw new IllegalArgumentException("invalid_request");
        }
    }

    // 비밀번호 형식 검사
    private boolean checkPasswordFormat(String password) {
        return password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,20}$");
    }
}