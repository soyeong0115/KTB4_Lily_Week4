package org.example.communityapi.user;

import org.example.communityapi.user.dto.ProfileResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private static final int USER_ID = 1;
    private static final String EMAIL = "test@test.com";
    private static final String PASSWORD = "encoded-password";
    private static final String NICKNAME = "테스트";
    private static final String PROFILE_IMAGE = null;

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("내 프로필 조회 성공")
    void getProfile_Success() {
        // given
        User user = createUser();

        given(userRepository.findById(USER_ID)).willReturn(Optional.of(user));

        // when
        ProfileResponse response = userService.getProfile(USER_ID);

        // then
        assertThat(response.getEmail()).isEqualTo(EMAIL);
        assertThat(response.getNickname()).isEqualTo(NICKNAME);
        assertThat(response.getProfileImage()).isEqualTo(PROFILE_IMAGE);

        verify(userRepository).findById(USER_ID);
    }

    private User createUser() {
        return new User(
                EMAIL,
                PASSWORD,
                NICKNAME,
                PROFILE_IMAGE
        );
    }

    @Test
    @DisplayName("프로필 수정 성공")
    void updateProfile_Success() {
    }

    @Test
    @DisplayName("중복 닉네임이면 프로필 수정 실패")
    void updateProfile_DuplicatedNickname_ThrowsException() {
    }

    @Test
    @DisplayName("비밀번호 수정 성공")
    void updatePassword_Success() {
    }

    @Test
    @DisplayName("비밀번호와 비밀번호 확인이 다르면 수정 실패")
    void updatePassword_PasswordCheckMismatch_ThrowsException() {
    }

    @Test
    @DisplayName("회원 탈퇴 성공")
    void deleteUser_Success() {
    }
}