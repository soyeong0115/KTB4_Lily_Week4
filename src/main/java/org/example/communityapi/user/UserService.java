package org.example.communityapi.user;

import org.example.communityapi.user.dto.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.example.communityapi.user.dto.ProfileResponse;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 프로필 조회
    public ProfileResponse getProfile(Integer userId) {
        User user = findActiveUser(userId);

        return new ProfileResponse(
                user.getUserId(),
                user.getEmail(),
                user.getNickname(),
                user.getProfileImage()
        );
    }

    // 프로필 수정
    public UpdateProfileResponse updateProfile(Integer userId, UpdateProfileRequest request) {
        User user = findActiveUser(userId);

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

        user.updateProfile(
                request.getNickname(),
                request.getProfileImage()
        );

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
        User user = findActiveUser(userId);

        if (user == null) {
            throw new IllegalArgumentException("unauthorized");
        }

        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new IllegalArgumentException("invalid_request");
        }

        if (request.getNewPassword() == null || request.getNewPassword().isBlank()) {
            throw new IllegalArgumentException("invalid_request");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("password_mismatch");
        }

        if (!checkPasswordFormat(request.getNewPassword())) {
            throw new IllegalArgumentException("invalid_request");
        }

        String encodedNewPassword = passwordEncoder.encode(request.getNewPassword());

        user.updatePassword(encodedNewPassword);
    }

    // 회원 탈퇴
    public void deleteUser(Integer userId) {
        User user = findActiveUser(userId);

        if (user == null) {
            throw new IllegalArgumentException("unauthorized");
        }

        user.delete();
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

    // 인증된 활성 사용자 조회
    private User findActiveUser(Integer userId) {
        if (userId == null) {
            throw new IllegalArgumentException("unauthorized");
        }

        return userRepository.findByUserIdAndDeletedFalse(userId)
                .orElseThrow(() -> new IllegalArgumentException("unauthorized"));
    }
}