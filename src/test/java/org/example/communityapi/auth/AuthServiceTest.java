package org.example.communityapi.auth;

import org.example.communityapi.auth.dto.SignupRequest;
import org.example.communityapi.jwt.JwtProvider;
import org.example.communityapi.user.UserRepository;
import org.example.communityapi.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtProvider jwtProvider;

    private static final String EMAIL = "test@test.com";
    private static final String PASSWORD = "Password123!";
    private static final String NICKNAME = "테스트";
    private static final String PROFILE_IMAGE = null;
    private static final String ENCODED_PASSWORD = "encoded-password";

    @Test
    @DisplayName("회원가입 성공")
    void signup_Success() {
        // given
        SignupRequest request = createSignupRequest(
                EMAIL,
                PASSWORD,
                NICKNAME,
                PROFILE_IMAGE
        );

        given(passwordEncoder.encode(PASSWORD)).willReturn(ENCODED_PASSWORD);

        // when
        authService.signup(request);

        // then
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();

        assertThat(savedUser.getEmail()).isEqualTo(EMAIL);
        assertThat(savedUser.getNickname()).isEqualTo(NICKNAME);
        assertThat(savedUser.getPassword()).isEqualTo(ENCODED_PASSWORD);

        verify(passwordEncoder).encode(PASSWORD);
    }

    private SignupRequest createSignupRequest(
            String email,
            String password,
            String nickname,
            String profileImage
    ) {
        return new SignupRequest(email, password, nickname, profileImage);
    }

    @Test
    @DisplayName("올바른 이메일 주소 형식이 아니면 회원가입 실패")
    void signup_InvalidEmail_ThrowsException() {
        // given
        SignupRequest request = createSignupRequest(
                "invalid-email",
                PASSWORD,
                NICKNAME,
                PROFILE_IMAGE
        );

        // when + then
        assertThatThrownBy(() -> authService.signup(request))
                .isInstanceOf(IllegalArgumentException.class);

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("중복 이메일이면 회원가입 실패")
    void signup_DuplicatedEmail_ThrowsException() {
        // given
        SignupRequest request = createSignupRequest(
                EMAIL,
                PASSWORD,
                NICKNAME,
                PROFILE_IMAGE
        );

        given(userRepository.existsByEmail(EMAIL)).willReturn(true);

        // when + then
        assertThatThrownBy(() -> authService.signup(request))
                .isInstanceOf(IllegalArgumentException.class);

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("중복 닉네임이면 회원가입 실패")
    void signup_DuplicatedNickname_ThrowsException() {
    }

    @Test
    @DisplayName("비밀번호 유효성 통과 못하면 회원가입 실패")
    void signup_InvalidPassword_ThrowsException() {
    }

    @Test
    @DisplayName("로그인 성공 시 accessToken 반환")
    void login_Success_ReturnsAccessToken() {
    }

    @Test
    @DisplayName("비밀번호가 틀리면 로그인 실패")
    void login_WrongPassword_ThrowsException() {
    }

    @Test
    @DisplayName("존재하지 않는 이메일이면 로그인 실패")
    void login_EmailNotFound_ThrowsException() {
    }

    @Test
    @DisplayName("탈퇴한 유저는 로그인 실패")
    void login_DeletedUser_ThrowsException() {
    }
}