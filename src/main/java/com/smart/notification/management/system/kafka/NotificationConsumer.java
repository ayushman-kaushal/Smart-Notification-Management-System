package com.smart.notification.management.system.kafka;

import com.smart.notification.management.system.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationConsumer {

	private final NotificationService notificationService;

	private static final Logger log = org.slf4j.LoggerFactory.getLogger(NotificationConsumer.class);

	@KafkaListener(
			topics = "${notification.kafka.topic}",
			groupId = "notification-group"
	)
	public void consume(NotificationMessage message) {
		log.info("Message Received with notificationId :  {}" , message.getNotificationId());
		notificationService.processNotification(
				message.getNotificationId());
	}
}
