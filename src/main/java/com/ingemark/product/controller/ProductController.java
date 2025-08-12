package com.ingemark.product.controller;

import com.ingemark.product.controller.dto.ProductDto;
import com.ingemark.product.controller.mapper.ProductMapper;
import com.ingemark.product.exception.ProductNotFoundException;
import com.ingemark.product.model.Product;
import com.ingemark.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ProductController handles HTTP requests related to Product entities.
 * It provides endpoints for creating, retrieving, and listing products.
 */
@RestController
@RequestMapping("/products")
public class ProductController {
	/** Service for managing Product entities. */
	private final ProductService service;

	/** Mapper for converting between Product and ProductDto objects. */
	private final ProductMapper productMapper = new ProductMapper();

	/**
	 * Constructs a ProductController with the specified ProductService.
	 *
	 * @param service the ProductService for managing products
	 */
	public ProductController(ProductService service) {
		this.service = service;
	}

	/**
	 * Creates a new Product and saves it to the database.
	 * Converts the product price from EUR to USD using the current exchange rate.
	 *
	 * @param productDto the Product dto to create
	 * @return the created Product with USD price set, otherwise an error response.
	 */
	@PostMapping
	public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDto productDto) {
		try {
			Product product = service.createProduct(productMapper.mapFrom(productDto));
			return ResponseEntity.status(HttpStatus.CREATED).body(productMapper.mapTo(product));
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Failed to create product: " + ex.getMessage());
		}
	}

	/**
	 * Retrieves a Product by its code and converts its price from EUR to USD.
	 *
	 * @param code the Code of the Product to retrieve
	 * @return the Product with USD price set, otherwise an error response.
	 */
	@GetMapping("/{code}")
	public ResponseEntity<?> getProductByCode(@PathVariable String code) {
		try {
			Product product = service.getProductByCode(code);
			return ResponseEntity.ok(productMapper.mapTo(product));
		} catch (ProductNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Failed to fetch product: " + ex.getMessage());
		}
	}

	/**
	 * Retrieves all Products from the database and converts their prices from EUR to USD.
	 *
	 * @return a list of Products dto with USD prices set, otherwise an error response.
	 */
	@GetMapping
	public ResponseEntity<List<?>> getAllProducts() {
		List<ProductDto> products = service.getAllProducts().stream()
				.map(productMapper::mapTo)
				.toList();
		if (products.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return ResponseEntity.ok(products);
	}
}