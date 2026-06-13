package com.smart.notification.management.system.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

import com.smart.notification.management.system.enums.NotificationType;

@Data
public class CreateNotificationRequest {

    @NotNull
    private Long userId;

    @NotNull
    private NotificationType type;

    @NotBlank
    private String message;

    private LocalDateTime scheduleTime;
}