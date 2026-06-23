package org.example.communityapi.auth;

import org.example.communityapi.auth.dto.LoginRequest;
import org.example.communityapi.auth.dto.LoginResponse;
import org.example.communityapi.auth.dto.SignupRequest;
import org.example.communityapi.auth.dto.SignupResponse;
import org.example.communityapi.user.User;
import org.example.communityapi.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;

    // UserRepository를 주입받기
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 회원가입 처리
    public SignupResponse signup(SignupRequest request) {
        checkSignupRequest(request);

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("email_duplicated");
        }

        if (userRepository.existsByNickname(request.getNickname())) {
            throw new IllegalArgumentException("nickname_duplicated");
        }

        User user = new User(
                request.getEmail(),
                request.getPassword(),
                request.getNickname(),
                request.getProfileImage()
        );

        userRepository.save(user);

        return new SignupResponse(user.getUserId());
    }

    // 로그인 처리
    public LoginResponse login(LoginRequest request) {
        checkLoginRequest(request);

        User user = userRepository.findByEmail(request.getEmail());

        if (user == null) {
            throw new IllegalArgumentException("login_failed");
        }

        if (!user.getPassword().equals(request.getPassword())) {
            throw new IllegalArgumentException("login_failed");
        }

        return new LoginResponse(user.getUserId());
    }

    // 회원가입 요청값 검증
    private void checkSignupRequest(SignupRequest request) {
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new IllegalArgumentException("invalid_request");
        }

        if (!checkEmailFormat(request.getEmail())) {
            throw new IllegalArgumentException("invalid_email_format");
        }

        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new IllegalArgumentException("invalid_request");
        }

        if (!checkPasswordFormat(request.getPassword())) {
            throw new IllegalArgumentException("invalid_password_format");
        }

        if (request.getNickname() == null || request.getNickname().isBlank()) {
            throw new IllegalArgumentException("invalid_request");
        }

        if (request.getNickname().contains(" ") || request.getNickname().length() > 10) {
            throw new IllegalArgumentException("invalid_nickname_format");
        }
    }

    // 로그인 요청값 검증
    private void checkLoginRequest(LoginRequest request) {
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new IllegalArgumentException("invalid_request");
        }

        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new IllegalArgumentException("invalid_request");
        }
    }

    // 이메일 형식 검증
    private boolean checkEmailFormat(String email) {
        return email.matches("^[A-Za-z]+@[A-Za-z]+\\.[A-Za-z]+$");
    }

    // 비밀번호 형식 검증
    private boolean checkPasswordFormat(String password) {
        return password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,20}$");
    }
}