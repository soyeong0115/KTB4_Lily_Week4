package org.example.communityapi.comment;

import org.example.communityapi.comment.dto.CreateCommentRequest;
import org.example.communityapi.comment.dto.CreateCommentResponse;
import org.example.communityapi.comment.dto.UpdateCommentRequest;
import org.example.communityapi.comment.dto.UpdateCommentResponse;
import org.example.communityapi.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {

    private final CommentService commentService;

    // CommentService 주입하기
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // 댓글 작성 API
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<ApiResponse<?>> createComment(
            @RequestHeader(value = "X-USER-ID", required = false) Integer userId,
            @PathVariable int postId,
            @RequestBody CreateCommentRequest request
    ) {
        try {
            CreateCommentResponse response = commentService.createComment(userId, postId, request);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(ApiResponse.success("create_comment_success", response));

        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals("unauthorized")) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponse.fail("unauthorized"));
            }

            if (e.getMessage().equals("post_not_found")) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.fail("post_not_found"));
            }

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.fail("invalid_request"));
        }
    }

    // 댓글 수정 API
    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse<?>> updateComment(
            @RequestHeader(value = "X-USER-ID", required = false) Integer userId,
            @PathVariable int commentId,
            @RequestBody UpdateCommentRequest request
    ) {
        try {
            UpdateCommentResponse response = commentService.updateComment(userId, commentId, request);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ApiResponse.success("update_comment_success", response));

        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals("unauthorized")) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponse.fail("unauthorized"));
            }

            if (e.getMessage().equals("forbidden")) {
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body(ApiResponse.fail("forbidden"));
            }

            if (e.getMessage().equals("comment_not_found")) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.fail("comment_not_found"));
            }

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.fail("invalid_request"));
        }
    }

    // 댓글 삭제 API
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(
            @RequestHeader(value = "X-USER-ID", required = false) Integer userId,
            @PathVariable int commentId
    ) {
        try {
            commentService.deleteComment(userId, commentId);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ApiResponse.fail("delete_comment_success"));

        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals("unauthorized")) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponse.fail("unauthorized"));
            }

            if (e.getMessage().equals("forbidden")) {
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body(ApiResponse.fail("forbidden"));
            }

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.fail("comment_not_found"));
        }
    }
}