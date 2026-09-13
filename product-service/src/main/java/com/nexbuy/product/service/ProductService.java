package com.nexbuy.product.service;

import java.util.List;

import com.nexbuy.product.dto.ProductRequest;
import com.nexbuy.product.dto.ProductResponse;
import com.nexbuy.product.entity.Category;

public interface ProductService {
	
	ProductResponse createProduct(ProductRequest product);

	ProductResponse getProductById(Long id);

	List<ProductResponse> getAllProducts();

	ProductResponse updateProduct(Long id, ProductRequest request);

	void deleteProduct(Long id);

	List<ProductResponse> searchProducts(String name, Category category);

	ProductResponse getStock(Long id);

	ProductResponse reduceStock(Long id, Integer quantity);

	ProductResponse increaseStock(Long id, Integer quantity);
}
