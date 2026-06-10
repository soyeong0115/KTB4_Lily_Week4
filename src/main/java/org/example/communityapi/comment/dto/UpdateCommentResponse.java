package org.example.communityapi.comment.dto;

public class UpdateCommentResponse {

    private int commentId;

    public UpdateCommentResponse(int commentId) {
        this.commentId = commentId;
    }

    public int getCommentId() {
        return commentId;
    }
}