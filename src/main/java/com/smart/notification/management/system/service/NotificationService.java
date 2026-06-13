package com.smart.notification.management.system.service;

import org.springframework.data.domain.Page;

import com.smart.notification.management.system.dto.request.CreateNotificationRequest;
import com.smart.notification.management.system.dto.response.NotificationResponse;

public interface NotificationService {

    NotificationResponse create(
            CreateNotificationRequest request);

    NotificationResponse getById(Long id);

    Page<NotificationResponse> getAll(
            String status,
            String type,
            int page,
            int size);

    void retry(Long id);

    void processNotification(Long id);
}