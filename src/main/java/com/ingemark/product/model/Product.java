package com.ingemark.product.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * Product represents an item in the inventory with a unique code, name, price in EUR,
 * and availability status. The price in USD is transient and not persisted in the database.
 */
@Entity
public class Product {
	/** Unique identifier for the product. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** Unique code for the product, must be exactly 10 characters long. */
	@Column(unique = true, length = 10, nullable = false)
	private String code;

	/** Name of the product, cannot be blank. */
	private String name;

	/** Price of the product in EUR, must be a non-negative value.*/
	private BigDecimal priceEur;

	/**
	 * Price of the product in USD, calculated from the EUR price using an external API.
	 * This field is transient and not stored in the database.
	 */
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