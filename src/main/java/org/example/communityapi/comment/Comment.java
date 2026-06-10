package org.example.communityapi.comment;

public class Comment {

    private int commentId;
    private int postId;
    private String content;
    private String createdAt;

    private int writerId;
    private String writerNickname;
    private String writerProfileImage;

    public Comment(
            int commentId,
            int postId,
            String content,
            String createdAt,
            int writerId,
            String writerNickname,
            String writerProfileImage
    ) {
        this.commentId = commentId;
        this.postId = postId;
        this.content = content;
        this.createdAt = createdAt;
        this.writerId = writerId;
        this.writerNickname = writerNickname;
        this.writerProfileImage = writerProfileImage;
    }

    public int getCommentId() {
        return commentId;
    }

    public int getPostId() {
        return postId;
    }

    public String getContent() {
        return content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public int getWriterId() {
        return writerId;
    }

    public String getWriterNickname() {
        return writerNickname;
    }

    public String getWriterProfileImage() {
        return writerProfileImage;
    }

    // 댓글 수정
    public void update(String content) {
        this.content = content;
    }
}