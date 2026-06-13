package com.smart.notification.management.system.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.smart.notification.management.system.dto.response.ApiResponse;
import com.smart.notification.management.system.dto.response.DashboardResponse;
import com.smart.notification.management.system.service.DashBoardService;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

	private final DashBoardService dashboardService;

	@GetMapping
	public ApiResponse<DashboardResponse> dashboard() {

		return ApiResponse.<DashboardResponse>builder()
				.success(true).data(dashboardService.getDashboard()).build();
	}
}
