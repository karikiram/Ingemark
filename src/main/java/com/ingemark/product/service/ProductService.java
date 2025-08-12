package com.ingemark.product.service;

import com.ingemark.product.exception.ProductNotFoundException;
import com.ingemark.product.hnb.service.HnbApiService;
import com.ingemark.product.model.Product;
import com.ingemark.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

/**
 * ProductService provides methods to manage Product entities.
 * It interacts with the ProductRepository for database operations
 * and HnbApiService to fetch exchange rates for price conversion.
 */
@Service
public class ProductService {
	/** Repository for accessing Product entities in the database. */
	private final ProductRepository repository;

	/** Service for fetching exchange rates from the HNB API. */
	private final HnbApiService hnbApiService;

	/**
	 * Constructs a ProductService with the specified repository and HNB API service.
	 *
	 * @param repository the ProductRepository for database operations
	 * @param hnbApiService the HnbApiService for fetching exchange rates
	 */
	public ProductService(ProductRepository repository, HnbApiService hnbApiService) {
		this.repository = repository;
		this.hnbApiService = hnbApiService;
	}

	/**
	 * Creates a new Product and saves it to the database.
	 * Converts the product price from EUR to USD using the current exchange rate.
	 *
	 * @param product the Product to create
	 * @return the created Product with USD price set
	 */
	public Product createProduct(Product product) {
		BigDecimal exchangeRate = hnbApiService.getEurToUsdRate();
		product.setPriceUsd(product.getPriceEur().multiply(exchangeRate));
		return repository.save(product);
	}

	/**
	 * Retrieves a Product by its code and converts its price from EUR to USD.
	 *
	 * @param code the code of the Product to retrieve
	 * @return the Product with USD price set
	 */
	public Product getProductByCode(String code) {
		Product product = (Product) repository.findByCode(code)
				.orElseThrow(() -> new ProductNotFoundException(code));
		BigDecimal exchangeRate = hnbApiService.getEurToUsdRate();
		product.setPriceUsd(product.getPriceEur().multiply(exchangeRate));
		return product;
	}

	/**
	 * Retrieves all Products from the database and converts their prices from EUR to USD.
	 *
	 * @return a list of Products with USD prices set
	 */
	public List<Product> getAllProducts() {
		BigDecimal exchangeRate = hnbApiService.getEurToUsdRate();
		List<Product> products = repository.findAll();
		products.forEach(p -> p.setPriceUsd(p.getPriceEur().multiply(exchangeRate)));
		return products;
	}
}