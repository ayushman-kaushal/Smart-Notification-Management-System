package com.smart.notification.management.system.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

import com.smart.notification.management.system.enums.NotificationStatus;
import com.smart.notification.management.system.enums.NotificationType;

@Data
@Builder
public class NotificationResponse {

    private Long id;

    private Long userId;

    private NotificationType type;

    private String message;

    private NotificationStatus status;

    private Integer retryCount;

    private LocalDateTime scheduleTime;

    private LocalDateTime processedAt;

    private LocalDateTime createdAt;
}