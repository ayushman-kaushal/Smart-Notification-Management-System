package com.smart.notification.management.system.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import com.smart.notification.management.system.enums.NotificationStatus;
import com.smart.notification.management.system.enums.NotificationType;

@Entity
@Table(name = "notifications", indexes = { @Index(name = "idx_status", columnList = "status"),
		@Index(name = "idx_type", columnList = "type"), @Index(name = "idx_created_at", columnList = "createdAt") })
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long userId;

	@Enumerated(EnumType.STRING)
	private NotificationType type;

	@Column(length = 2000)
	private String message;

	@Enumerated(EnumType.STRING)
	private NotificationStatus status;

	private Integer retryCount;

	private LocalDateTime scheduleTime;

	private LocalDateTime lastRetryAt;

	private LocalDateTime processedAt;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	@PrePersist
	public void prePersist() {
		createdAt = LocalDateTime.now();
		updatedAt = LocalDateTime.now();

		if (retryCount == null) {
			retryCount = 0;
		}
	}

	@PreUpdate
	public void preUpdate() {
		updatedAt = LocalDateTime.now();
	}
}
