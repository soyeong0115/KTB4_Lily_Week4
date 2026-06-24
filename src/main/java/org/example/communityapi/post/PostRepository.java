package org.example.communityapi.post;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    // 삭제 X 게시글 목록 조회
    List<Post> findByIsDeletedFalse();
}