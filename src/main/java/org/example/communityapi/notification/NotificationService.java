package org.example.communityapi.notification;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.communityapi.notification.dto.NotificationResponse;
import org.example.communityapi.post.Post;
import org.example.communityapi.user.User;
import org.example.communityapi.user.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
@Transactional
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

    public void markAsRead(Integer userId, int notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new IllegalArgumentException("notification_not_found"));

        if (notification.getReceiver().getUserId() != userId) {
            throw new IllegalArgumentException("forbidden");
        }

        notification.markAsRead();
    }

    public void markAllAsRead(Integer userId) {
        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            throw new IllegalArgumentException("unauthorized");
        }

        List<Notification> notifications =
                notificationRepository.findByReceiverAndIsReadFalse(user);

        for (Notification notification : notifications) {
            notification.markAsRead();
        }
    }

    public void deleteAllNotifications(Integer userId) {
        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            throw new IllegalArgumentException("unauthorized");
        }

        notificationRepository.deleteByReceiver(user);
    }

    @Scheduled(cron = "0 0 4 * * *")
    public void deleteOldNotifications() {
        notificationRepository.deleteByCreatedAtBefore(LocalDateTime.now().minusDays(14));
    }
}
