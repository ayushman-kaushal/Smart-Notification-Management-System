package com.smart.notification.management.system.controller;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.smart.notification.management.system.dto.request.CreateNotificationRequest;
import com.smart.notification.management.system.dto.response.ApiResponse;
import com.smart.notification.management.system.dto.response.NotificationResponse;
import com.smart.notification.management.system.service.NotificationService;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

	private final NotificationService service;

	@PostMapping
	public ApiResponse<NotificationResponse> create(@Valid @RequestBody CreateNotificationRequest request) {

		return ApiResponse.<NotificationResponse>builder().success(true).message("Notification created")
				.data(service.create(request)).build();
	}

	@GetMapping("/{id}")
	public ApiResponse<NotificationResponse> getById(@PathVariable Long id) {

		return ApiResponse.<NotificationResponse>builder().success(true).data(service.getById(id)).build();
	}

	@GetMapping
	public ApiResponse<Page<NotificationResponse>> getAll(

			@RequestParam(required = false) String status,

			@RequestParam(required = false) String type,

			@RequestParam(defaultValue = "0") int page,

			@RequestParam(defaultValue = "10") int size) {

		return ApiResponse.<Page<NotificationResponse>>builder().success(true)
				.data(service.getAll(status, type, page, size)).build();
	}

	@PostMapping("/{id}/retry")
	public ApiResponse<String> retry(@PathVariable Long id) {

		service.retry(id);

		return ApiResponse.<String>builder().success(true).message("Retry queued successfully").build();
	}
}
