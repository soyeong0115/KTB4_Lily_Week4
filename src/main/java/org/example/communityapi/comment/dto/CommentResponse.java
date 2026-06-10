package org.example.communityapi.comment.dto;

import org.example.communityapi.post.dto.WriterResponse;

public class CommentResponse {

    private int commentId;
    private String content;
    private String createdAt;
    private WriterResponse writer;

    public CommentResponse(int commentId, String content, String createdAt, WriterResponse writer) {
        this.commentId = commentId;
        this.content = content;
        this.createdAt = createdAt;
        this.writer = writer;
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
}