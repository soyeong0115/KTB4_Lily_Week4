package org.example.communityapi.post.dto;

import org.example.communityapi.comment.dto.CommentResponse;

import java.util.List;

public class PostDetailResponse {

    private int postId;
    private String title;
    private String content;
    private String postImage;
    private String createdAt;
    private int likeCount;
    private int viewCount;
    private int commentCount;
    private WriterResponse writer;
    private List<CommentResponse> comments;

    public PostDetailResponse(
            int postId,
            String title,
            String content,
            String postImage,
            String createdAt,
            int likeCount,
            int viewCount,
            int commentCount,
            WriterResponse writer,
            List<CommentResponse> comments
    ) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.postImage = postImage;
        this.createdAt = createdAt;
        this.likeCount = likeCount;
        this.viewCount = viewCount;
        this.commentCount = commentCount;
        this.writer = writer;
        this.comments = comments;
    }

    public int getPostId() {
        return postId;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public int getViewCount() {
        return viewCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public WriterResponse getWriter() {
        return writer;
    }

    public List<CommentResponse> getComments() {
        return comments;
    }
}