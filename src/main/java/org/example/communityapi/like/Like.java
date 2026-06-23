package org.example.communityapi.like;

import jakarta.persistence.*;
import org.example.communityapi.post.Post;
import org.example.communityapi.user.User;

@Entity
@Table(name = "likes")
public class Like {

    // 좋아요 번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private int likeId;

    // 사용자 번호
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 게시글 번호
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    protected Like() {
    }

    public Like(User user, Post post) {
        this.user = user;
        this.post = post;
    }

    public int getLikeId() {
        return likeId;
    }

    public User getUser() {
        return user;
    }

    public Post getPost() {
        return post;
    }
}