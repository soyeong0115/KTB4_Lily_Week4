package org.example.communityapi.notification;

import org.example.communityapi.notification.dto.NotificationResponse;
import org.example.communityapi.post.Post;
import org.example.communityapi.user.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationWebSocketHandler notificationWebSocketHandler;

    public NotificationService(
            NotificationRepository notificationRepository,
            NotificationWebSocketHandler notificationWebSocketHandler
    ) {
        this.notificationRepository = notificationRepository;
        this.notificationWebSocketHandler = notificationWebSocketHandler;
    }

    public void notify(
            User receiver,
            Post post,
            String type,
            String content
    ) {
        Notification notification = new Notification(
                receiver,
                post,
                type,
                content
        );
        notificationRepository.save(notification);

        NotificationResponse response = new NotificationResponse(
                notification.getNotificationId(),
                notification.getType(),
                notification.getContent(),
                post.getPostId(),
                notification.isRead(),
                notification.getCreatedAt()
        );




    }


}
