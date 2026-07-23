package org.example.communityapi.post.dto;

public class PostListResponse {

    private int postId;
    private String titlePreview;
    private String contentPreview;
    private String createdAt;
    private int commentCount;
    private int likeCount;
    private int viewCount;
    private WriterResponse writer;
    private String postImage;

    public PostListResponse(
            int postId,
            String titlePreview,
            String contentPreview,
            String createdAt,
            int commentCount,
            int likeCount,
            int viewCount,
            WriterResponse writer,
            String postImage
    ) {
        this.postId = postId;
        this.titlePreview = titlePreview;
        this.contentPreview = contentPreview;
        this.createdAt = createdAt;
        this.commentCount = commentCount;
        this.likeCount = likeCount;
        this.viewCount = viewCount;
        this.writer = writer;
        this.postImage = postImage;
    }

    public int getPostId() {
        return postId;
    }

    public String getTitlePreview() {
        return titlePreview;
    }

    public String getContentPreview() {
        return contentPreview;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public int getViewCount() {
        return viewCount;
    }

    public WriterResponse getWriter() {
        return writer;
    }

    public String getPostImage() {
        return postImage;
    }
}