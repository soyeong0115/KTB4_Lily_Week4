package org.example.communityapi.like;

import org.example.communityapi.post.Post;
import org.example.communityapi.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Integer> {

    // 좋아요 눌렀는지 확인
    boolean existsByUserAndPost(User user, Post post);

    // 특정 좋아요 찾기
    Like findByUserAndPost(User user, Post post);
}