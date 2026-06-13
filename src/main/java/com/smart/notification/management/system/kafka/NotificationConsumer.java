package com.smart.notification.management.system.kafka;

import com.smart.notification.management.system.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationConsumer {

	private final NotificationService notificationService;

	@KafkaListener(
			topics = "${notification.kafka.topic}",
			groupId = "notification-group"
	)
	public void consume(NotificationMessage message) {

		notificationService.processNotification(
				message.getNotificationId());
	}
}
