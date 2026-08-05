package org.example.communityapi.notification.dto;

import java.time.LocalDateTime;

public class NotificationResponse {
    private int notificationId;
    private String type;
    private String content;
    private int postId;
    private boolean isRead;
    private LocalDateTime createdAt;

    public NotificationResponse(
            int notificationId,
            String type,
            String content,
            int postId,
            boolean isRead,
            LocalDateTime createdAt
    ) {
        this.notificationId = notificationId;
        this.type = type;
        this.content = content;
        this.postId = postId;
        this.isRead = isRead;
        this.createdAt = createdAt;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public int getPostId() {
        return postId;
    }

    public boolean isRead() {
        return isRead;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
