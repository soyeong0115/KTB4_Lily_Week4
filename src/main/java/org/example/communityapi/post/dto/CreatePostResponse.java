package org.example.communityapi.post.dto;

public class CreatePostResponse {

    private int postId;

    public CreatePostResponse(int postId) {
        this.postId = postId;
    }

    public int getPostId() {
        return postId;
    }
}