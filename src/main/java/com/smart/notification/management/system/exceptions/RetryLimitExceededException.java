package com.smart.notification.management.system.exceptions;

public class RetryLimitExceededException extends RuntimeException {

	public RetryLimitExceededException(String message) {
		super(message);
	}
}
