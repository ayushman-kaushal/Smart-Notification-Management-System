package com.smart.notification.management.system.service;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.smart.notification.management.system.dto.response.DashboardResponse;
import com.smart.notification.management.system.enums.NotificationStatus;
import com.smart.notification.management.system.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashBoardService {

	private final NotificationRepository repository;

	@Override
	public DashboardResponse getDashboard() {

		Map<String, Long> typeStats = repository.findAll().stream()
				.collect(Collectors.groupingBy(n -> n.getType().name(), Collectors.counting()));

		return DashboardResponse.builder().totalNotifications(repository.count())
				.sentNotifications(repository.countByStatus(NotificationStatus.SENT))
				.failedNotifications(repository.countByStatus(NotificationStatus.FAILED))
				.pendingNotifications(repository.countByStatus(NotificationStatus.PENDING))
				.retryingNotifications(repository.countByStatus(NotificationStatus.RETRYING)).typeStatistics(typeStats)
				.build();
	}
}