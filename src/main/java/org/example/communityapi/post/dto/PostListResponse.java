package org.example.communityapi.post.dto;

public class PostListResponse {

    private int postId;
    private String titlePreview;
    private String createdAt;
    private int commentCount;
    private int likeCount;
    private int viewCount;
    private WriterResponse writer;

    public PostListResponse(
            int postId,
            String titlePreview,
            String createdAt,
            int commentCount,
            int likeCount,
            int viewCount,
            WriterResponse writer
    ) {
        this.postId = postId;
        this.titlePreview = titlePreview;
        this.createdAt = createdAt;
        this.commentCount = commentCount;
        this.likeCount = likeCount;
        this.viewCount = viewCount;
        this.writer = writer;
    }

    public int getPostId() {
        return postId;
    }

    public String getTitlePreview() {
        return titlePreview;
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
}