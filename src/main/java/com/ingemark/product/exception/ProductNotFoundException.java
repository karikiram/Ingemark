package com.ingemark.product.exception;

/**
 * ProductNotFoundException is thrown when a product with a specific code is not found.
 * It extends RuntimeException to indicate that it is an unchecked exception.
 */
public class ProductNotFoundException extends RuntimeException {
	/**
	 * Constructs a new ProductNotFoundException with a default message.
	 */
	public ProductNotFoundException(String code) {
		super("Product with code " + code + " not found.");
	}
}