package org.example.communityapi.notification;

import org.example.communityapi.common.ApiResponse;
import org.example.communityapi.notification.dto.NotificationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(
            NotificationService notificationService
    ) {
        this.notificationService = notificationService;
    }

    // 알림 전체 조회 API
    @GetMapping
    public ResponseEntity<ApiResponse<?>> getNotifications(
            @AuthenticationPrincipal Integer userId
    ) {
        try {
            List<NotificationResponse> response = notificationService.getNotifications(userId);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ApiResponse.success("get_notifications_success", response));
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.fail("unauthorized"));
        }
    }

    // 알림 읽음 처리 API
    @PatchMapping("/{notificationId}")
    public ResponseEntity<ApiResponse<Void>> markAsRead(
            @AuthenticationPrincipal Integer userId,
            @PathVariable int notificationId
    ) {
        try {
            notificationService.markAsRead(userId, notificationId);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ApiResponse.success("mark_as_read_success", null));
        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals("forbidden")) {
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body(ApiResponse.fail("forbidden"));
            }

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.fail("notification_not_found"));
        }
    }
}
