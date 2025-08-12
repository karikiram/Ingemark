package com.ingemark.product.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

/**
 * ProductDto represents a Data Transfer Object for the Product entity.
 * It contains fields for product code, name, price in EUR, price in USD,
 * and availability status. This DTO is used for transferring product data
 * between the client and server.
 */
public class ProductDto {
	/** Unique code for the product, must be exactly 10 characters long. */
	@NotBlank(message = "Code cannot be empty")
	@Size(min = 10, max = 10, message = "Code must be exactly 10 characters long")
	private String code;

	/** Name of the product, cannot be blank. */
	@NotBlank(message = "Name cannot be empty")
	private String name;

	/** Price of the product in EUR, must be a non-negative value. */
	@NotBlank(message = "Price must be specified")
	private BigDecimal priceEur;

	/** Price of the product in USD, calculated from the EUR price using an external API. */
	private BigDecimal priceUsd;

	/** Availability status of the product, true if available, false otherwise. */
	private boolean isAvailable;

	/**
	 * Gets the unique identifier of the product.
	 *
	 * @return the ID of the product
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the unique code for the product.
	 *
	 * @param code the unique code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Gets the unique identifier of the product.
	 *
	 * @return the ID of the product
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the product.
	 *
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the price of the product in EUR.
	 *
	 * @return the price in EUR
	 */
	public BigDecimal getPriceEur() {
		return priceEur;
	}

	/**
	 * Sets the price of the product in EUR.
	 *
	 * @param priceEur the price in EUR to set
	 */
	public void setPriceEur(BigDecimal priceEur) {
		this.priceEur = priceEur;
	}

	/**
	 * Gets the price of the product in USD.
	 *
	 * @return the price in USD
	 */
	public BigDecimal getPriceUsd() {
		return priceUsd;
	}

	/**
	 * Sets the price of the product in USD.
	 *
	 * @param priceUsd the price in USD to set
	 */
	public void setPriceUsd(BigDecimal priceUsd) {
		this.priceUsd = priceUsd;
	}

	/**
	 * Checks if the product is available.
	 *
	 * @return true if the product is available, false otherwise
	 */
	public boolean isAvailable() {
		return isAvailable;
	}

	/**
	 * Sets the availability status of the product.
	 *
	 * @param available true if the product is available, false otherwise
	 */
	public void setAvailable(boolean available) {
		isAvailable = available;
	}
}
