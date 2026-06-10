package org.example.communityapi.post;

public class Post {

    private int postId;
    private String title;
    private String content;
    private String postImage;
    private String createdAt;
    private int likeCount;
    private int viewCount;

    private int writerId;
    private String writerNickname;
    private String writerProfileImage;

    public Post(
            int postId,
            String title,
            String content,
            String postImage,
            String createdAt,
            int likeCount,
            int viewCount,
            int writerId,
            String writerNickname,
            String writerProfileImage
    ) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.postImage = postImage;
        this.createdAt = createdAt;
        this.likeCount = likeCount;
        this.viewCount = viewCount;
        this.writerId = writerId;
        this.writerNickname = writerNickname;
        this.writerProfileImage = writerProfileImage;
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

    public int getWriterId() {
        return writerId;
    }

    public String getWriterNickname() {
        return writerNickname;
    }

    public String getWriterProfileImage() {
        return writerProfileImage;
    }

    // 게시글 수정
    public void update(String title, String content, String postImage) {
        if (title != null) {
            this.title = title;
        }
        if (content != null) {
            this.content = content;
        }
        if (postImage != null) {
            this.postImage = postImage;
        }
    }

    // 좋아요 등록 - 증가
    public void increaseLikeCount() {
        this.likeCount = this.likeCount + 1;
    }

    // 좋아요 취소 - 감소
    public void decreaseLikeCount() {
        if (this.likeCount > 0) {
            this.likeCount = this.likeCount - 1;
        }
    }
}