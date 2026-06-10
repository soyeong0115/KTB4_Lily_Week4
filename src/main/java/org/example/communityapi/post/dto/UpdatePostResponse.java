package org.example.communityapi.post.dto;

public class UpdatePostResponse {

    private int postId;

    public UpdatePostResponse(int postId) {
        this.postId = postId;
    }

    public int getPostId() {
        return postId;
    }
}