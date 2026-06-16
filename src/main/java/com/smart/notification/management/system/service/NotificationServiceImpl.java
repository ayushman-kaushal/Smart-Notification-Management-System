package com.smart.notification.management.system.service;

import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.smart.notification.management.system.dto.request.CreateNotificationRequest;
import com.smart.notification.management.system.dto.response.NotificationResponse;
import com.smart.notification.management.system.entity.Notification;
import com.smart.notification.management.system.enums.NotificationStatus;
import com.smart.notification.management.system.enums.NotificationType;
import com.smart.notification.management.system.exceptions.DuplicateNotificationException;
import com.smart.notification.management.system.exceptions.InvalidRetryException;
import com.smart.notification.management.system.exceptions.ResourceNotFoundException;
import com.smart.notification.management.system.exceptions.RetryLimitExceededException;
import com.smart.notification.management.system.kafka.NotificationProducer;
import com.smart.notification.management.system.repository.NotificationRepository;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

	private final NotificationRepository repository;
	private final NotificationProducer producer;
	private final ModelMapper modelMapper;

	private final Random random = new Random();

	private static final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);

	@Override
	public NotificationResponse create(CreateNotificationRequest request) {

		validateRepeatedWords(request.getMessage());

		validateDuplicateNotification(request);

		Notification notification = Notification.builder().userId(request.getUserId()).type(request.getType())
				.message(request.getMessage()).scheduleTime(request.getScheduleTime())
				.status(NotificationStatus.PENDING).retryCount(0).build();

		notification = repository.save(notification);

		log.info("Sending notification --> {}", notification.getType());
		producer.send(notification.getId());

		return map(notification);
	}

	@Override
	public NotificationResponse getById(Long id) {

		Notification notification = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Notification not found"));

		return map(notification);
	}

	@Override
	public Page<NotificationResponse> getAll(String status, String type, int page, int size) {

		Specification<Notification> spec =
				Specification.where((Specification<Notification>) null);
		if (status != null) {
			spec = spec.and((root, query, cb) -> cb.equal(root.get("status"), NotificationStatus.valueOf(status)));
		}

		if (type != null) {
			spec = spec.and((root, query, cb) -> cb.equal(root.get("type"), NotificationType.valueOf(type)));
		}

		Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

		return repository.findAll(spec, pageable).map(this::map);
	}

	@Override
	public void retry(Long id) {

		Notification notification = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Notification not found"));

		if (notification.getStatus() != NotificationStatus.FAILED) {

			throw new InvalidRetryException("Only FAILED notification can be retried");
		}

		if (notification.getRetryCount() >= 3) {

			throw new RetryLimitExceededException("Maximum retry limit reached");
		}

		if (notification.getLastRetryAt() != null
				&& notification.getLastRetryAt().isAfter(LocalDateTime.now().minusMinutes(2))) {

			throw new InvalidRetryException("Retry allowed only after 2 minutes");
		}

		notification.setStatus(NotificationStatus.RETRYING);

		notification.setRetryCount(notification.getRetryCount() + 1);

		notification.setLastRetryAt(LocalDateTime.now());

		repository.save(notification);

		producer.send(notification.getId());
	}

	@Override
	public void processNotification(Long id) {

		Notification notification = repository.findById(id).orElse(null);

		if (notification == null) {
			return;
		}

		boolean fail = random.nextInt(100) < 30;

		if (fail) {

			notification.setStatus(NotificationStatus.FAILED);

		} else {

			notification.setStatus(NotificationStatus.SENT);

			notification.setProcessedAt(LocalDateTime.now());
		}
		log.info("Messaage Consumed with status --> {}", notification.getStatus());
		repository.save(notification);
	}

	private void validateDuplicateNotification(CreateNotificationRequest request) {

		LocalDateTime fiveMinutesAgo = LocalDateTime.now().minusMinutes(5);

		boolean duplicate = !repository
				.findDuplicates(request.getUserId(), request.getType(), request.getMessage(), fiveMinutesAgo).isEmpty();

		if (duplicate) {

			throw new DuplicateNotificationException("Duplicate notification within 5 minutes");
		}
	}

	private void validateRepeatedWords(String message) {

		String[] words = message.split("\\s+");

		int count = 1;

		for (int i = 1; i < words.length; i++) {

			if (words[i].equalsIgnoreCase(words[i - 1])) {

				count++;

				if (count > 3) {

					throw new IllegalArgumentException("Word repeated more than 3 times consecutively");
				}

			} else {
				count = 1;
			}
		}
	}

	private NotificationResponse map(Notification notification) {

		return NotificationResponse.builder().id(notification.getId()).userId(notification.getUserId())
				.type(notification.getType()).message(notification.getMessage()).status(notification.getStatus())
				.retryCount(notification.getRetryCount()).scheduleTime(notification.getScheduleTime())
				.processedAt(notification.getProcessedAt()).createdAt(notification.getCreatedAt()).build();
	}

	private NotificationResponse mapping(Notification notification) {

		return modelMapper.map(notification, NotificationResponse.class);
	}
}
