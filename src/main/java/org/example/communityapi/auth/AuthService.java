package org.example.communityapi.auth;

import org.example.communityapi.auth.dto.LoginRequest;
import org.example.communityapi.auth.dto.LoginResponse;
import org.example.communityapi.auth.dto.SignupRequest;
import org.example.communityapi.auth.dto.SignupResponse;
import org.example.communityapi.jwt.JwtProvider;
import org.example.communityapi.user.User;
import org.example.communityapi.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, JwtProvider jwtProvider, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
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

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = new User(
                request.getEmail(),
                encodedPassword,
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

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("login_failed");
        }

        String accessToken = jwtProvider.createToken(user.getUserId());

        return new LoginResponse(accessToken);
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
        return email.matches("^[A-Za-z0-9]+@[A-Za-z0-9]+\\.[A-Za-z]+$");
    }

    // 비밀번호 형식 검증
    private boolean checkPasswordFormat(String password) {
        return password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,20}$");
    }
}