package org.example.communityapi.comment;

import org.example.communityapi.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    // 삭제되지 않은 댓글 + 탈퇴하지 않은 작성자의 댓글 조회
    List<Comment> findByPostAndIsDeletedFalseAndWriter_DeletedFalse(Post post);

    // 삭제되지 않은 댓글 + 탈퇴하지 않은 작성자의 댓글 개수 조회
    int countByPostAndIsDeletedFalseAndWriter_DeletedFalse(Post post);
}