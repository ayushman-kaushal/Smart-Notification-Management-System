package com.smart.notification.management.system.config;

import com.smart.notification.management.system.kafka.NotificationMessage;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic notificationTopic() {

        return TopicBuilder
                .name("notification-topic")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public KafkaTemplate<String, NotificationMessage> kafkaTemplate(
            ProducerFactory<String, NotificationMessage> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

}
