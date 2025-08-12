package com.ingemark.product.repository;

import com.ingemark.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * ProductRepository provides methods to access and manipulate Product entities in the database.
 * It extends JpaRepository to leverage built-in CRUD operations and custom query methods.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
	/**
	 * Finds a Product by its code.
	 *
	 * @param code the code of the Product to find
	 * @return an Optional containing the Product if found, or empty if not found
	 */
	Optional<Object> findByCode(String code);

	// Additional custom query methods can be defined here if needed
	// For example, to find products by name or category, etc.
}
