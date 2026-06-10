package org.example.communityapi.post;

import org.example.communityapi.common.ApiResponse;
import org.example.communityapi.post.dto.CreatePostRequest;
import org.example.communityapi.post.dto.CreatePostResponse;
import org.example.communityapi.post.dto.PostDetailResponse;
import org.example.communityapi.post.dto.PostListResponse;
import org.example.communityapi.post.dto.UpdatePostRequest;
import org.example.communityapi.post.dto.UpdatePostResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    // PostService 주입
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 게시글 작성 API
    @PostMapping
    public ResponseEntity<ApiResponse<?>> createPost(
            @RequestHeader(value = "X-USER-ID", required = false) Integer userId,
            @RequestBody CreatePostRequest request
    ) {
        try {
            CreatePostResponse response = postService.createPost(userId, request);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(ApiResponse.success("create_post_success", response));

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

    // 게시글 목록 조회 API
    @GetMapping
    public ResponseEntity<ApiResponse<List<PostListResponse>>> getPosts() {
        List<PostListResponse> response = postService.getPosts();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success("get_posts_success", response));
    }

    // 게시글 및 댓글 상세 조회 API
    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<?>> getPostDetail(@PathVariable int postId) {
        try {
            PostDetailResponse response = postService.getPostDetail(postId);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ApiResponse.success("get_post_success", response));

        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.fail("post_not_found"));
        }
    }

    // 게시글 수정 API
    @PatchMapping("/{postId}")
    public ResponseEntity<ApiResponse<?>> updatePost(
            @RequestHeader(value = "X-USER-ID", required = false) Integer userId,
            @PathVariable int postId,
            @RequestBody UpdatePostRequest request
    ) {
        try {
            UpdatePostResponse response = postService.updatePost(userId, postId, request);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ApiResponse.success("update_post_success", response));

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

    // 게시글 삭제 API
    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> deletePost(
            @RequestHeader(value = "X-USER-ID", required = false) Integer userId,
            @PathVariable int postId
    ) {
        try {
            postService.deletePost(userId, postId);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ApiResponse.fail("delete_success"));

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
                    .body(ApiResponse.fail("post_not_found"));
        }
    }
}