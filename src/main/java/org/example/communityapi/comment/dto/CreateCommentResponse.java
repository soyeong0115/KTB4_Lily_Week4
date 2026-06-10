package org.example.communityapi.comment.dto;

public class CreateCommentResponse {

    private int commentId;

    public CreateCommentResponse(int commentId) {
        this.commentId = commentId;
    }

    public int getCommentId() {
        return commentId;
    }
}