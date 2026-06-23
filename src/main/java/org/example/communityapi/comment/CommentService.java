package org.example.communityapi.comment;

import org.example.communityapi.comment.dto.CreateCommentRequest;
import org.example.communityapi.comment.dto.CreateCommentResponse;
import org.example.communityapi.comment.dto.UpdateCommentRequest;
import org.example.communityapi.comment.dto.UpdateCommentResponse;
import org.example.communityapi.post.Post;
import org.example.communityapi.post.PostRepository;
import org.example.communityapi.user.User;
import org.example.communityapi.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // Repository 주입하기
    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    // 댓글 작성
    public CreateCommentResponse createComment(Integer userId, int postId, CreateCommentRequest request) {
        if (userId == null) {
            throw new IllegalArgumentException("unauthorized");
        }

        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            throw new IllegalArgumentException("unauthorized");
        }

        Post post = postRepository.findById(postId).orElse(null);

        if (post == null) {
            throw new IllegalArgumentException("post_not_found");
        }

        if (request.getContent() == null || request.getContent().isBlank()) {
            throw new IllegalArgumentException("invalid_request");
        }

        Comment comment = new Comment(
                post,
                request.getContent(),
                "2026-06-10 10:10:00",
                user
        );

        commentRepository.save(comment);

        return new CreateCommentResponse(comment.getCommentId());
    }

    // 댓글 수정
    public UpdateCommentResponse updateComment(Integer userId, int commentId, UpdateCommentRequest request) {
        if (userId == null) {
            throw new IllegalArgumentException("unauthorized");
        }

        Comment comment = commentRepository.findById(commentId).orElse(null);

        if (comment == null) {
            throw new IllegalArgumentException("comment_not_found");
        }

        if (comment.getWriter().getUserId() != userId) {
            throw new IllegalArgumentException("forbidden");
        }

        if (request.getContent() == null || request.getContent().isBlank()) {
            throw new IllegalArgumentException("invalid_request");
        }

        comment.update(request.getContent());

        return new UpdateCommentResponse(comment.getCommentId());
    }

    // 댓글 삭제
    public void deleteComment(Integer userId, int commentId) {
        if (userId == null) {
            throw new IllegalArgumentException("unauthorized");
        }

        Comment comment = commentRepository.findById(commentId).orElse(null);

        if (comment == null) {
            throw new IllegalArgumentException("comment_not_found");
        }

        if (comment.getWriter().getUserId() != userId) {
            throw new IllegalArgumentException("forbidden");
        }

        commentRepository.delete(comment);
    }
}