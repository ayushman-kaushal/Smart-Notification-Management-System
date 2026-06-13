package com.smart.notification.management.system.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import com.smart.notification.management.system.entity.Notification;
import com.smart.notification.management.system.enums.NotificationStatus;
import com.smart.notification.management.system.enums.NotificationType;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationRepository
		extends JpaRepository<Notification, Long>, JpaSpecificationExecutor<Notification> {

	@Query("""
			SELECT n
			FROM Notification n
			WHERE n.userId = :userId
			AND n.type = :type
			AND n.message = :message
			AND n.createdAt >= :time
			""")
	List<Notification> findDuplicates(@Param("userId") Long userId, @Param("type") NotificationType type,
			@Param("message") String message, @Param("time") LocalDateTime time);

	long countByStatus(NotificationStatus status);
}
