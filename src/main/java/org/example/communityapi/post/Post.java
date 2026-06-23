package org.example.communityapi.post;

import jakarta.persistence.*;
import org.example.communityapi.user.User;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 게시글 번호
    @Column(name = "post_id")
    private int postId;

    // 제목
    @Column(nullable = false)
    private String title;

    // 내용
    @Column(nullable = false)
    private String content;

    // 이미지
    @Column(name = "post_image")
    private String postImage;

    // 작성 시간
    @Column(name = "created_at")
    private String createdAt;

    // 수정 시간
    @Column(name = "updated_at")
    private String updatedAt;

    // 댓글수

    // 좋아요수
    @Column(name = "like_count")
    private int likeCount;

    // 조회수
    @Column(name = "view_count")
    private int viewCount;

    // 사용자 번호
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User writer;

    protected Post() {
    }

    public Post(
            String title,
            String content,
            String postImage,
            String createdAt,
            User writer
    ) {
        this.title = title;
        this.content = content;
        this.postImage = postImage;
        this.createdAt = createdAt;
        this.likeCount = 0;
        this.viewCount = 0;
        this.writer = writer;
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

    public User getWriter() {
        return writer;
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