package com.ingemark.product.controller.mapper;

import com.ingemark.product.controller.dto.ProductDto;
import com.ingemark.product.model.Product;
import org.springframework.stereotype.Component;

/**
 * ProductMapper is responsible for converting between Product and ProductDto objects.
 * It implements the DtoMapper interface to provide mapping functionality.
 */
@Component
public class ProductMapper implements DtoMapper<Product, ProductDto> {
	@Override
	public ProductDto mapTo(Product product) {
		ProductDto productDto = new ProductDto();
		productDto.setCode(product.getCode());
		productDto.setName(product.getName());
		productDto.setPriceEur(product.getPriceEur());
		productDto.setPriceUsd(product.getPriceUsd());
		productDto.setAvailable(product.isAvailable());
		return productDto;
	}

	@Override
	public Product mapFrom(ProductDto productDto) {
		Product product = new Product();
		product.setCode(productDto.getCode());
		product.setName(productDto.getName());
		product.setPriceEur(productDto.getPriceEur());
		product.setPriceUsd(productDto.getPriceUsd());
		product.setAvailable(productDto.isAvailable());
		return product;
	}
}
