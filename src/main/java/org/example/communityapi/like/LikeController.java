package org.example.communityapi.like;

import org.example.communityapi.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts/{postId}/likes")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    // 좋아요 등록 API
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createLike(
            @AuthenticationPrincipal Integer userId,
            @PathVariable int postId
    ) {
        try {
            likeService.createLike(userId, postId);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(ApiResponse.success("create_like_success", null));

        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals("unauthorized")) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponse.fail("unauthorized"));
            }

            if (e.getMessage().equals("like_already_exists")) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(ApiResponse.fail("like_already_exists"));
            }

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.fail("post_not_found"));
        }
    }

    // 좋아요 취소 API
    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteLike(
            @AuthenticationPrincipal Integer userId,
            @PathVariable int postId
    ) {
        try {
            likeService.deleteLike(userId, postId);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ApiResponse.success("delete_like_success", null));

        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals("unauthorized")) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponse.fail("unauthorized"));
            }

            if (e.getMessage().equals("like_not_found")) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.fail("like_not_found"));
            }

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.fail("post_not_found"));
        }
    }
}
