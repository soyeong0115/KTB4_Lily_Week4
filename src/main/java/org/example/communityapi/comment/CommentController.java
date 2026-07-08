package org.example.communityapi.comment;

import org.example.communityapi.comment.dto.CreateCommentRequest;
import org.example.communityapi.comment.dto.CreateCommentResponse;
import org.example.communityapi.comment.dto.UpdateCommentRequest;
import org.example.communityapi.comment.dto.UpdateCommentResponse;
import org.example.communityapi.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@CrossOrigin(
        origins = {
                "http://127.0.0.1:5500",
                "http://localhost:5500"
        },
        methods = {
                RequestMethod.POST,
                RequestMethod.PATCH,
                RequestMethod.DELETE,
                RequestMethod.OPTIONS
        },
        allowedHeaders = "*"
)
@RestController
@RequestMapping("/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    // CommentService 주입하기
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // 댓글 작성 API
    @PostMapping
    public ResponseEntity<ApiResponse<?>> createComment(
            @AuthenticationPrincipal Integer userId,
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
    @PatchMapping("/{commentId}")
    public ResponseEntity<ApiResponse<?>> updateComment(
            @AuthenticationPrincipal Integer userId,
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
    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(
            @AuthenticationPrincipal Integer userId,
            @PathVariable int commentId
    ) {
        try {
            commentService.deleteComment(userId, commentId);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ApiResponse.success("delete_comment_success", null));

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