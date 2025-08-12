package com.ingemark.product.service;

import com.ingemark.product.exception.ProductNotFoundException;
import com.ingemark.product.hnb.service.HnbApiService;
import com.ingemark.product.model.Product;
import com.ingemark.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * ProductServiceTest is a test class for the ProductService.
 * It uses Mockito to mock the ProductRepository and HnbApiService,
 * and tests the methods of ProductService for various scenarios.
 */
public class ProductServiceTest {
	/** Product repository mock */
	@Mock
	private ProductRepository productRepository;

	/** Hnb API service mock */
	@Mock
	private HnbApiService hnbApiService;

	/**
	 * ProductService is the service class that provides methods to manage products.
	 * It interacts with the ProductRepository for database operations and HnbApiService
	 * to fetch exchange rates for price conversion.
	 */
	@InjectMocks
	private ProductService productService;

	/**
	 * Initializes the mocks before each test.
	 * This method is called before each test method to set up the mock objects.
	 */
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	/**
	 * Tests the creation of a product with a valid price in EUR.
	 * Ensures that the service correctly converts the price to USD using the exchange rate.
	 */
	@Test
	public void testCreateProduct() {
		Product product = new Product();
		product.setPriceEur(BigDecimal.valueOf(10));
		BigDecimal exchangeRate = BigDecimal.valueOf(1.2);
		when(hnbApiService.getEurToUsdRate()).thenReturn(exchangeRate);
		when(productRepository.save(any(Product.class))).thenAnswer(i -> i.getArgument(0));
		Product result = productService.createProduct(product);
		assertEquals(BigDecimal.valueOf(12.0), result.getPriceUsd());
		verify(productRepository).save(product);
		verify(hnbApiService).getEurToUsdRate();
	}

	/**
	 * Tests the creation of a product with a null price in EUR.
	 * Ensures that the service throws a NullPointerException when the price is null.
	 */
	@Test
	public void testCreateProductWithNullPrice() {
		Product product = new Product();
		product.setPriceEur(null);
		when(hnbApiService.getEurToUsdRate()).thenReturn(BigDecimal.ONE);
		when(productRepository.save(any(Product.class))).thenAnswer(i -> i.getArgument(0));
		assertThrows(NullPointerException.class, () -> productService.createProduct(product));
	}

	/**
	 * Tests the creation of a product with a zero exchange rate.
	 * Ensures that the USD price is set to zero when the exchange rate is zero.
	 */
	@Test
	public void testCreateProductWithZeroExchangeRate() {
		Product product = new Product();
		product.setPriceEur(BigDecimal.valueOf(10));
		when(hnbApiService.getEurToUsdRate()).thenReturn(BigDecimal.ZERO);
		when(productRepository.save(any(Product.class))).thenAnswer(i -> i.getArgument(0));
		Product result = productService.createProduct(product);
		assertEquals(BigDecimal.ZERO, result.getPriceUsd());
	}

	/**
	 * Tests the retrieval of a product by its code and conversion of its price from EUR to USD.
	 * Ensures that the service correctly applies the exchange rate to the product's price.
	 */
	@Test
	public void testGetProductByCode() {
		Product product = new Product();
		product.setPriceEur(BigDecimal.valueOf(20));
		when(productRepository.findByCode("code1")).thenReturn(Optional.of(product));
		when(hnbApiService.getEurToUsdRate()).thenReturn(BigDecimal.valueOf(1.5));
		Product result = productService.getProductByCode("code1");
		assertEquals(BigDecimal.valueOf(30.0), result.getPriceUsd());
		verify(productRepository).findByCode("code1");
		verify(hnbApiService).getEurToUsdRate();
	}

	/**
	 * Tests the case when a product with the specified code does not exist.
	 * Ensures that the service throws a ProductNotFoundException.
	 */
	@Test
	public void testGetProductByCodeThrowsException() {
		when(productRepository.findByCode("notfound")).thenReturn(Optional.empty());
		assertThrows(ProductNotFoundException.class, () -> productService.getProductByCode("notfound"));
		verify(productRepository).findByCode("notfound");
		verifyNoInteractions(hnbApiService);
	}

	/**
	 * Tests the retrieval of all products and conversion of their prices from EUR to USD.
	 * Ensures that the service correctly applies the exchange rate to each product's price.
	 */
	@Test
	public void testGetAllProducts() {
		Product p1 = new Product();
		p1.setPriceEur(BigDecimal.valueOf(5));
		Product p2 = new Product();
		p2.setPriceEur(BigDecimal.valueOf(10));
		when(productRepository.findAll()).thenReturn(List.of(p1, p2));
		when(hnbApiService.getEurToUsdRate()).thenReturn(BigDecimal.valueOf(2));
		List<Product> products = productService.getAllProducts();
		assertEquals(BigDecimal.valueOf(10), products.get(0).getPriceUsd());
		assertEquals(BigDecimal.valueOf(20), products.get(1).getPriceUsd());
		verify(productRepository).findAll();
		verify(hnbApiService).getEurToUsdRate();
	}

	/**
	 * Tests the case when the product list is empty.
	 * Ensures that the service returns an empty list without errors.
	 */
	@Test
	public void testGetAllProductsEmpty() {
		when(productRepository.findAll()).thenReturn(Collections.emptyList());
		List<Product> products = productService.getAllProducts();
		assertNotNull(products);
		assertTrue(products.isEmpty());
		verify(productRepository).findAll();
		verify(hnbApiService).getEurToUsdRate();
	}
}