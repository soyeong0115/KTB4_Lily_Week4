package org.example.communityapi.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserServiceTest {

    @Test
    @DisplayName("내 프로필 조회 성공")
    void getProfile_Success() {
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