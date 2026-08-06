package org.example.communityapi.notification;

import jakarta.persistence.*;
import org.example.communityapi.post.Post;
import org.example.communityapi.user.User;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private int notificationId;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "type")
    private String type;

    @Column(name = "content")
    private String content;

    @Column(name = "is_read")
    private boolean isRead;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    protected Notification() {
    }

    public Notification(
            User receiver,
            Post post,
            String type,
            String content
    ) {
        this.receiver = receiver;
        this.post = post;
        this.type = type;
        this.content = content;
        this.isRead = false;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public User getReceiver() {
        return receiver;
    }

    public Post getPost() {
        return post;
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public boolean isRead() {
        return isRead;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // 알림 읽음 처리
    public void markAsRead() {
        this.isRead = true;
    }
}
