package com.smart.notification.management.system.kafka;

import org.slf4j.Logger;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationProducer {

	private final KafkaTemplate<String, NotificationMessage> kafkaTemplate;

	private static final Logger log = org.slf4j.LoggerFactory.getLogger(NotificationProducer.class);
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
