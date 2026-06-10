package org.example.communityapi.post.dto;

public class CreatePostRequest {

    private String title;
    private String content;
    private String postImage;

    public CreatePostRequest() {
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getPostImage() {
        return postImage;
    }
}