package com.smart.notification.management.system.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import com.smart.notification.management.system.dto.response.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiResponse<String> handleNotFound(Exception ex) {

		return ApiResponse.<String>builder().success(false).message(ex.getMessage()).build();
	}

	@ExceptionHandler(DuplicateNotificationException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public ApiResponse<String> handleDuplicate(Exception ex) {

		return ApiResponse.<String>builder().success(false).message(ex.getMessage()).build();
	}

	@ExceptionHandler({ InvalidRetryException.class, RetryLimitExceededException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiResponse<String> handleRetry(Exception ex) {

		return ApiResponse.<String>builder().success(false).message(ex.getMessage()).build();
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiResponse<String> handleValidation(MethodArgumentNotValidException ex) {

		String message = ex.getBindingResult().getFieldError().getDefaultMessage();

		return ApiResponse.<String>builder().success(false).message(message).build();
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ApiResponse<String> handleGeneric(Exception ex) {

		return ApiResponse.<String>builder().success(false).message(ex.getMessage()).build();
	}
}
