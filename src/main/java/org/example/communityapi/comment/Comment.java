package org.example.communityapi.comment;

import jakarta.persistence.*;

import org.example.communityapi.user.User;
import org.example.communityapi.post.Post;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    // 댓글 번호
    @Column(name = "comment_id")
    private int commentId;

    // 내용
    @Column(nullable = false)
    private String content;

    // 작성 시간
    @Column(name = "created_at")
    private String createdAt;

    // 수정 시간
    @Column(name = "updated_at")
    private String updatedAt;

    // 삭제 여부
    @Column(name = "is_deleted")
    private boolean isDeleted;

    // 사용자 번호
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User writer;

    // 게시글 번호
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    protected Comment() {
    }

    public Comment(
            Post post,
            String content,
            String createdAt,
            User writer
    ) {
        this.post = post;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = createdAt;
        this.isDeleted = false;
        this.writer = writer;
    }

    public int getCommentId() {
        return commentId;
    }

    public Post getPost() {
        return post;
    }

    public String getContent() {
        return content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public User getWriter() {
        return writer;
    }

    // 댓글 수정
    public void update(String content, String updatedAt) {
        this.content = content;
        this.updatedAt = updatedAt;
    }

    // 댓글 삭제
    public void delete() {
        this.isDeleted = true;
    }
}