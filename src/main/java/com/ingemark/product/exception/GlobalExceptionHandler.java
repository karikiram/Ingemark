package com.ingemark.product.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * GlobalExceptionHandler handles exceptions thrown by the application.
 * It provides custom responses for validation errors and product not found exceptions.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * Handles MethodArgumentNotValidException, which occurs when validation fails on a request body.
	 * It collects all validation errors and returns them in a structured format.
	 *
	 * @param ex the exception containing validation errors
	 * @return a ResponseEntity with a map of field errors and their messages
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error ->
				errors.put(error.getField(), error.getDefaultMessage())
		);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}

	/**
	 * Handles ProductNotFoundException, which is thrown when a product with a specific code is not found.
	 * It returns a 404 Not Found response with the exception message.
	 *
	 * @param ex the exception that was thrown
	 * @return a ResponseEntity with a 404 status and the exception message
	 */
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<String> handleProductNotFound(ProductNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}
}
