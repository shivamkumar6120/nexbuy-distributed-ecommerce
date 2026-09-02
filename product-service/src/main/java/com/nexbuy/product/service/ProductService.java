package com.nexbuy.product.service;

import java.util.List;

import com.nexbuy.product.dto.ProductRequest;
import com.nexbuy.product.dto.ProductResponse;

public interface ProductService {
	ProductResponse createProduct(ProductRequest product);
	ProductResponse getProductById(Long id);
	List<ProductResponse> getAllProducts();
	ProductResponse updateProduct(Long id, ProductRequest request);
	void deleteProduct (Long id);
}
