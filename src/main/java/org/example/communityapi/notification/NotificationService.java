package org.example.communityapi.notification;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.communityapi.notification.dto.NotificationResponse;
import org.example.communityapi.post.Post;
import org.example.communityapi.user.User;
import org.example.communityapi.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationWebSocketHandler notificationWebSocketHandler;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    public NotificationService(
            NotificationRepository notificationRepository,
            NotificationWebSocketHandler notificationWebSocketHandler,
            ObjectMapper objectMapper,
            UserRepository userRepository
    ) {
        this.notificationRepository = notificationRepository;
        this.notificationWebSocketHandler = notificationWebSocketHandler;
        this.objectMapper = objectMapper;
        this.userRepository = userRepository;
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

    public List<NotificationResponse> getNotifications(Integer userId) {
        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            throw new IllegalArgumentException("unauthorized");
        }

        List<Notification> notifications =
                notificationRepository.findByReceiverOrderByCreatedAtDesc(user);

        return notifications.stream()
                .map(n -> new NotificationResponse(
                        n.getNotificationId(),
                        n.getType(),
                        n.getContent(),
                        n.getPost().getPostId(),
                        n.isRead(),
                        n.getCreatedAt()
                )).toList();
    }
}
