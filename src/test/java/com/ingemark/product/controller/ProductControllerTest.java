package com.ingemark.product.controller;

import com.ingemark.product.controller.dto.ProductDto;
import com.ingemark.product.exception.ProductNotFoundException;
import com.ingemark.product.model.Product;
import com.ingemark.product.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * ProductControllerTest is a test class for the ProductController.
 * It uses Mockito to mock the ProductService and test the controller's methods.
 * The tests cover various scenarios including successful operations and error handling.
 */
public class ProductControllerTest {
	/** Product service mock */
	@Mock
	private ProductService productService;

	/** Product controller */
	@InjectMocks
	private ProductController productController;

	/**
	 * Initializes the mocks before each test.
	 * This method is called before each test method to set up the mock objects.
	 */
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	/**
	 * Tests the case when fetching all products returns an empty list.
	 * It verifies that the response status is NO_CONTENT and the body is null.
	 */
	@Test
	public void testGetAllProducts_EmptyList() {
		when(productService.getAllProducts()).thenReturn(Collections.emptyList());
		ResponseEntity<List<?>> response = productController.getAllProducts();
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		assertNull(response.getBody());
	}

	/**
	 * Tests the case when fetching all products returns a non-empty list.
	 * It verifies that the response status is OK and the body contains products.
	 */
	@Test
	public void testGetAllProducts_NonEmptyList() {
		Product product = new Product();
		product.setCode("P1");
		when(productService.getAllProducts()).thenReturn(Collections.singletonList(product));
		ResponseEntity<List<?>> response = productController.getAllProducts();
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
	}

	/**
	 * Tests the case when fetching a product by code is successful.
	 * It verifies that the response status is OK and the body contains the product.
	 */
	@Test
	public void testGetProductByCode_Success() {
		Product product = new Product();
		product.setCode("P1");
		when(productService.getProductByCode("P1")).thenReturn(product);
		ResponseEntity<?> response = productController.getProductByCode("P1");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
	}

	/**
	 * Tests the case when fetching a product by code returns null.
	 * It verifies that the response status is NOT_FOUND and contains an appropriate message.
	 */
	@Test
	public void testGetProductByCode_NotFound() {
		when(productService.getProductByCode("P2")).thenThrow(new ProductNotFoundException("testCode"));
		ResponseEntity<?> response = productController.getProductByCode("P2");
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals("Product with code testCode not found.", response.getBody());
	}

	/**
	 * Tests the case when fetching a product by code fails due to an exception.
	 * It verifies that the response status is INTERNAL_SERVER_ERROR and contains an error message.
	 */
	@Test
	public void testGetProductByCode_Exception() {
		when(productService.getProductByCode("P3")).thenThrow(new RuntimeException("DB error"));
		ResponseEntity<?> response = productController.getProductByCode("P3");
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertEquals("Failed to fetch product: DB error", response.getBody());
	}

	/**
	 * Tests the case when creating a product is successful.
	 * It verifies that the response status is CREATED and the body contains the created product.
	 */
	@Test
	public void testCreateProduct_Success() {
		ProductDto dto = new ProductDto();
		dto.setCode("P1");
		Product product = new Product();
		product.setCode("P1");
		when(productService.createProduct(any(Product.class))).thenReturn(product);
		ResponseEntity<?> response = productController.createProduct(dto);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(response.getBody());
	}

	/**
	 * Tests the case when creating a product fails due to a ProductNotFoundException.
	 */
	@Test
	public void testCreateProduct_Exception() {
		ProductDto dto = new ProductDto();
		dto.setCode("P1");
		when(productService.createProduct(any(Product.class))).thenThrow(new RuntimeException("DB error"));
		ResponseEntity<?> response = productController.createProduct(dto);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertEquals("Failed to create product: DB error", response.getBody());
	}

	/**
	 * Tests the case when creating a product fails due to a DataIntegrityViolationException.
	 * It verifies that the response status is CONFLICT and contains an appropriate message.
	 */
	@Test
	public void testCreateProduct_DataIntegrityViolation() {
		ProductDto dto = new ProductDto();
		dto.setCode("P1");
		when(productService.createProduct(any(Product.class)))
				.thenThrow(new org.springframework.dao.DataIntegrityViolationException("Duplicate"));

		ResponseEntity<?> response = productController.createProduct(dto);

		assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
		assertEquals("Product with code P1 already exists.", response.getBody());
	}
}