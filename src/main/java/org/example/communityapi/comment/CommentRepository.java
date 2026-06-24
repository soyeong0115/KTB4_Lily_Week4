package org.example.communityapi.comment;

import org.example.communityapi.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    // 삭제 X 댓글 목록 조회
    public List<Comment> findByPostAndIsDeletedFalse(Post post);

    // 삭제 X 댓글 개수 조회
    public int countByPostAndIsDeletedFalse(Post post);
}