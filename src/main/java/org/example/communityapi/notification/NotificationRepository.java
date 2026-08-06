package org.example.communityapi.notification;

import org.example.communityapi.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    // 알림 전체
    List<Notification> findByReceiverOrderByCreatedAtDesc(User receiver);
}
