package org.example.communityapi.user;

import org.example.communityapi.common.ApiResponse;
import org.example.communityapi.user.dto.NicknameCheckResponse;
import org.example.communityapi.user.dto.UpdatePasswordRequest;
import org.example.communityapi.user.dto.UpdateProfileRequest;
import org.example.communityapi.user.dto.UpdateProfileResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    // UserService 주입하기
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 프로필 수정 API
    @PatchMapping("/profile")
    public ResponseEntity<ApiResponse<?>> updateProfile(
            @RequestHeader(value = "X-USER-ID", required = false) Integer userId,
            @RequestBody UpdateProfileRequest request
    ) {
        try {
            UpdateProfileResponse response = userService.updateProfile(userId, request);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ApiResponse.success("update_profile_success", response));

        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals("unauthorized")) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponse.fail("unauthorized"));
            }

            if (e.getMessage().equals("nickname_duplicated")) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(ApiResponse.fail("nickname_duplicated"));
            }

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.fail("invalid_request"));
        }
    }

    // 닉네임 중복 확인 API
    @GetMapping("/nickname/check")
    public ResponseEntity<ApiResponse<NicknameCheckResponse>> checkNickname(
            @RequestParam String nickname
    ) {
        try {
            NicknameCheckResponse response = userService.checkNicknameDuplicated(nickname);

            if (!response.isAvailable()) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(new ApiResponse<>("nickname_duplicated", response));
            }

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ApiResponse.success("nickname_available", response));

        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>("invalid_request", null));
        }
    }

    // 비밀번호 수정 API
    @PatchMapping("/password")
    public ResponseEntity<ApiResponse<Void>> updatePassword(
            @RequestHeader(value = "X-USER-ID", required = false) Integer userId,
            @RequestBody UpdatePasswordRequest request
    ) {
        try {
            userService.updatePassword(userId, request);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ApiResponse.fail("update_password_success"));

        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals("unauthorized")) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponse.fail("unauthorized"));
            }

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.fail("invalid_request"));
        }
    }

    // 회원 탈퇴 API
    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteUser(
            @RequestHeader(value = "X-USER-ID", required = false) Integer userId
    ) {
        try {
            userService.deleteUser(userId);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ApiResponse.fail("delete_user_success"));

        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.fail("unauthorized"));
        }
    }
}