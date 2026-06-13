package com.smart.notification.management.system.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationProducer {

	private final KafkaTemplate<String, NotificationMessage> kafkaTemplate;

	public NotificationProducer(
			KafkaTemplate<String, NotificationMessage> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public void send(Long notificationId) {

		kafkaTemplate.send(
				"notification-topic",
				NotificationMessage.builder()
						.notificationId(notificationId)
						.build()
		);
	}
}
