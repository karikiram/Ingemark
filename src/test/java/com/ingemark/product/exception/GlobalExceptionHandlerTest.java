package com.ingemark.product.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

/**
 * GlobalExceptionHandlerTest is a test class for the GlobalExceptionHandler.
 * It tests the exception handling methods for ProductNotFoundException.
 */
public class GlobalExceptionHandlerTest {
	/**
	 * Tests the handling of MethodArgumentNotValidException.
	 * It verifies that the response contains validation errors in the expected format.
	 */
	@Test
	public void testHandleProductNotFoundException() {
		GlobalExceptionHandler handler = new GlobalExceptionHandler();
		ProductNotFoundException ex = new ProductNotFoundException("Product not found");
		ResponseEntity<String> response = handler.handleProductNotFound(ex);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertTrue(response.getBody().contains("Product not found"));
	}
}

