package com.smart.notification.management.system.dto.response;

import lombok.*;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DashboardResponse {

    private long totalNotifications;

    private long sentNotifications;

    private long failedNotifications;

    private long pendingNotifications;

    private long retryingNotifications;

    private Map<String, Long> typeStatistics;
}
