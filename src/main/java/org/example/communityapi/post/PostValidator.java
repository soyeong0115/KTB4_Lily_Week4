package org.example.communityapi.post;

import org.springframework.stereotype.Component;

@Component
public class PostValidator {

    private final PostRepository postRepository;

    public PostValidator(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // 조회 가능 게시글 조회
    public Post findActivePost(Integer postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("post_not_found"));

        if (post.isDeleted()) {
            throw new IllegalArgumentException("post_not_found");
        }

        if (post.getWriter().isDeleted()) {
            throw new IllegalArgumentException("post_not_found");
        }

        return post;
    }
}
