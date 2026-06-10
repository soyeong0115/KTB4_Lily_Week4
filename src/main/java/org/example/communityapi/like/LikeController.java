package org.example.communityapi.like;

import org.example.communityapi.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts/{postId}/likes")
public class LikeController {

    private final LikeService likeService;

    // LikeService 주입
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    // 좋아요 등록 API
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createLike(
            @RequestHeader(value = "X-USER-ID", required = false) Integer userId,
            @PathVariable int postId
    ) {
        try {
            likeService.createLike(userId, postId);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(ApiResponse.fail("create_like_success"));

        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals("unauthorized")) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponse.fail("unauthorized"));
            }

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.fail("post_not_found"));
        }
    }

    // 좋아요 취소 API
    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteLike(
            @RequestHeader(value = "X-USER-ID", required = false) Integer userId,
            @PathVariable int postId
    ) {
        try {
            likeService.deleteLike(userId, postId);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ApiResponse.fail("delete_like_success"));

        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals("unauthorized")) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponse.fail("unauthorized"));
            }

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.fail("post_not_found"));
        }
    }
}
