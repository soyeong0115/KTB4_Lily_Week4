package org.example.communityapi.post.dto;

public class UpdatePostRequest {

    private String title;
    private String content;
    private String postImage;

    public UpdatePostRequest() {
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