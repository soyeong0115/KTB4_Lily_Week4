package org.example.communityapi.user;

import org.example.communityapi.user.dto.ProfileResponse;
import org.example.communityapi.user.dto.UpdateProfileRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
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
        // given
        User user = createUser();

        String patchNickname = "중복닉네임";
        String newProfileImage = "new-profile.png";

        given(userRepository.findById(USER_ID)).willReturn(Optional.of(user));
        given(userRepository.existsByNickname(patchNickname)).willReturn(false);

        UpdateProfileRequest request = createUpdateProfileRequest(patchNickname, newProfileImage);

        // when
        userService.updateProfile(USER_ID, request);

        // then
        assertThat(user.getNickname()).isEqualTo(patchNickname);
        assertThat(user.getProfileImage()).isEqualTo(newProfileImage);

        verify(userRepository).findById(USER_ID);
        verify(userRepository).existsByNickname(patchNickname);
    }

    private UpdateProfileRequest createUpdateProfileRequest(
            String nickname,
            String profileImage
    ) {
        return new UpdateProfileRequest(nickname, profileImage);
    }

    @Test
    @DisplayName("중복 닉네임이면 프로필 수정 실패")
    void updateProfile_DuplicatedNickname_ThrowsException() {
        // given
        User user = createUser();

        String duplicatedNickname = "중복닉네임";
        String newProfileImage = "new-profile.png";

        given(userRepository.findById(USER_ID)).willReturn(Optional.of(user));
        given(userRepository.existsByNickname(duplicatedNickname)).willReturn(true);

        UpdateProfileRequest request = createUpdateProfileRequest(duplicatedNickname, newProfileImage);

        // when + then
        assertThatThrownBy(() -> userService.updateProfile(USER_ID, request))
                .isInstanceOf(IllegalArgumentException.class);

        assertThat(user.getNickname()).isEqualTo(NICKNAME);
        assertThat(user.getProfileImage()).isEqualTo(PROFILE_IMAGE);

        verify(userRepository).findById(USER_ID);
        verify(userRepository).existsByNickname(duplicatedNickname);
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