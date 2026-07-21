package org.example.communityapi.like;

import org.example.communityapi.post.Post;
import org.example.communityapi.post.PostValidator;
import org.example.communityapi.user.User;
import org.example.communityapi.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LikeService {

    private final PostValidator postValidator;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    public LikeService(
            PostValidator postValidator,
            UserRepository userRepository,
            LikeRepository likeRepository
    ) {
        this.postValidator = postValidator;
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
    }

    // 좋아요 등록
    public void createLike(Integer userId, int postId) {
        if (userId == null) {
            throw new IllegalArgumentException("unauthorized");
        }

        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            throw new IllegalArgumentException("unauthorized");
        }

        Post post = postValidator.findActivePost(postId);

        if (likeRepository.existsByUserAndPost(user, post)) {
            throw new IllegalArgumentException("like_already_exists");
        }

        Like like = new Like(user, post);

        likeRepository.save(like);

        post.increaseLikeCount();
    }

    // 좋아요 취소
    public void deleteLike(Integer userId, int postId) {
        if (userId == null) {
            throw new IllegalArgumentException("unauthorized");
        }

        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            throw new IllegalArgumentException("unauthorized");
        }

        Post post = postValidator.findActivePost(postId);

        Like like = likeRepository.findByUserAndPost(user, post);

        if (like == null) {
            throw new IllegalArgumentException("like_not_found");
        }

        likeRepository.delete(like);

        post.decreaseLikeCount();
    }
}