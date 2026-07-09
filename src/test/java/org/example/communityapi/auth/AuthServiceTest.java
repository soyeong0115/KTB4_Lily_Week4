package org.example.communityapi.auth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AuthServiceTest {

    @Test
    @DisplayName("회원가입 성공")
    void signUp_success() {
    }

    @Test
    @DisplayName("올바른 이메일 주소 형식이 아니면 회원가입 실패")
    void signUp_shouldFail_whenEmailFormatIsInvalid() {
    }

    @Test
    @DisplayName("중복 이메일이면 회원가입 실패")
    void signUp_shouldFail_whenEmailIsDuplicated() {
    }

    @Test
    @DisplayName("중복 닉네임이면 회원가입 실패")
    void signUp_shouldFail_whenNicknameIsDuplicated() {
    }

    @Test
    @DisplayName("비밀번호 유효성 통과 못하면 회원가입 실패")
    void signUp_shouldFail_whenPasswordIsInvalid() {
    }

    @Test
    @DisplayName("비밀번호와 비밀번호 확인이 다르면 회원가입 실패")
    void signUp_shouldFail_whenPasswordCheckDoesNotMatch() {
    }

    @Test
    @DisplayName("회원가입 시 비밀번호가 암호화되어 저장된다")
    void signUp_shouldEncodePassword_whenRequestIsValid() {
    }

    @Test
    @DisplayName("로그인 성공 시 accessToken 반환")
    void login_shouldReturnAccessToken_whenLoginSuccess() {
    }

    @Test
    @DisplayName("비밀번호가 틀리면 로그인 실패")
    void login_shouldFail_whenPasswordIsWrong() {
    }

    @Test
    @DisplayName("존재하지 않는 이메일이면 로그인 실패")
    void login_shouldFail_whenEmailDoesNotExist() {
    }

    @Test
    @DisplayName("탈퇴한 유저는 로그인 실패")
    void login_shouldFail_whenUserIsDeleted() {
    }
}