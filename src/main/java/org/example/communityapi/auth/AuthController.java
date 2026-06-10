package org.example.communityapi.auth;

import org.example.communityapi.auth.dto.LoginRequest;
import org.example.communityapi.auth.dto.LoginResponse;
import org.example.communityapi.auth.dto.SignupRequest;
import org.example.communityapi.auth.dto.SignupResponse;
import org.example.communityapi.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    // AuthService를 주입받음
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // 회원가입 API
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<?>> signup(@RequestBody SignupRequest request) {
        try {
            SignupResponse response = authService.signup(request);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(ApiResponse.success("register_success", response));

        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals("email_duplicated")) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(ApiResponse.fail("email_duplicated"));
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

    // 로그인 API
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@RequestBody LoginRequest request) {
        try {
            LoginResponse response = authService.login(request);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ApiResponse.success("login_success", response));

        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals("login_failed")) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponse.fail("login_failed"));
            }

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.fail("invalid_request"));
        }
    }
}