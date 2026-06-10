package org.example.communityapi.post;

import org.example.communityapi.common.ApiResponse;
import org.example.communityapi.post.dto.PostDetailResponse;
import org.example.communityapi.post.dto.PostListResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    // PostService 주입하기
    public PostController(PostService postService) {
        this.postService = postService;
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
}