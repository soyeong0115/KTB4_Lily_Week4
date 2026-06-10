package org.example.communityapi.comment;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CommentRepository {

    private final Map<Integer, Comment> comments = new HashMap<>();
    private int sequence = 1;

    // 댓글 저장
    public Comment save(
            int postId,
            String content,
            String createdAt,
            int writerId,
            String writerNickname,
            String writerProfileImage
    ) {
        int commentId = sequence;
        sequence = sequence + 1;

        Comment comment = new Comment(
                commentId,
                postId,
                content,
                createdAt,
                writerId,
                writerNickname,
                writerProfileImage
        );

        comments.put(commentId, comment);

        return comment;
    }

    // 특정 게시글의 댓글 목록 조회
    public List<Comment> findByPostId(int postId) {
        List<Comment> result = new ArrayList<>();

        int commentId = 1;

        while (commentId < sequence) {
            Comment comment = comments.get(commentId);

            if (comment != null && comment.getPostId() == postId) {
                result.add(comment);
            }
            commentId = commentId + 1;
        }

        return result;
    }

    // 특정 게시글의 댓글 개수 조회
    public int countByPostId(int postId) {
        int count = 0;
        int commentId = 1;

        while (commentId < sequence) {
            Comment comment = comments.get(commentId);

            if (comment != null && comment.getPostId() == postId) {
                count = count + 1;
            }
            commentId = commentId + 1;
        }

        return count;
    }

    // commentId로 댓글 하나 조회
    public Comment findById(int commentId) {
        return comments.get(commentId);
    }

    // 댓글 삭제
    public void delete(int commentId) {
        comments.remove(commentId);
    }
}