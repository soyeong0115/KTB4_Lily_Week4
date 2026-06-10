package org.example.communityapi.like;

import org.example.communityapi.post.Post;
import org.example.communityapi.post.PostRepository;
import org.example.communityapi.user.User;
import org.example.communityapi.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // Repository 주입하기
    public LikeService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    // 좋아요 등록
    public void createLike(Integer userId, int postId) {
        if (userId == null) {
            throw new IllegalArgumentException("unauthorized");
        }

        User user = userRepository.findById(userId);

        if (user == null) {
            throw new IllegalArgumentException("unauthorized");
        }

        Post post = postRepository.findById(postId);

        if (post == null) {
            throw new IllegalArgumentException("post_not_found");
        }

        post.increaseLikeCount();
    }

    // 좋아요 취소
    public void deleteLike(Integer userId, int postId) {
        if (userId == null) {
            throw new IllegalArgumentException("unauthorized");
        }

        User user = userRepository.findById(userId);

        if (user == null) {
            throw new IllegalArgumentException("unauthorized");
        }

        Post post = postRepository.findById(postId);

        if (post == null) {
            throw new IllegalArgumentException("post_not_found");
        }

        post.decreaseLikeCount();
    }
}