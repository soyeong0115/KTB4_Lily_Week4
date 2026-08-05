package org.example.communityapi.notification;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.communityapi.notification.dto.NotificationResponse;
import org.example.communityapi.post.Post;
import org.example.communityapi.user.User;
import org.springframework.stereotype.Service;


@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationWebSocketHandler notificationWebSocketHandler;
    private final ObjectMapper objectMapper;

    public NotificationService(
            NotificationRepository notificationRepository,
            NotificationWebSocketHandler notificationWebSocketHandler,
            ObjectMapper objectMapper
    ) {
        this.notificationRepository = notificationRepository;
        this.notificationWebSocketHandler = notificationWebSocketHandler;
        this.objectMapper = objectMapper;
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

        try {
            String json = objectMapper.writeValueAsString(response);
            notificationWebSocketHandler.sendToUser(receiver.getUserId(), json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
