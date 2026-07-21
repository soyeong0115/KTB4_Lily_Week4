package org.example.communityapi.comment.dto;

import org.example.communityapi.post.dto.WriterResponse;

public class CommentResponse {

    private int commentId;
    private String content;
    private String createdAt;
    private WriterResponse writer;
    private boolean isMyComment;

    public CommentResponse(
            int commentId,
            String content,
            String createdAt,
            WriterResponse writer,
            Boolean isMyComment
    ) {
        this.commentId = commentId;
        this.content = content;
        this.createdAt = createdAt;
        this.writer = writer;
        this.isMyComment = isMyComment;
    }

    public int getCommentId() {
        return commentId;
    }

    public String getContent() {
        return content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public WriterResponse getWriter() {
        return writer;
    }

    public boolean isMyComment() {
        return isMyComment;
    }
}