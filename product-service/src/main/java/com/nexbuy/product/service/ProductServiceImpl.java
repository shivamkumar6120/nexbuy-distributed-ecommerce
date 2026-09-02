package com.nexbuy.product.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.nexbuy.product.dto.ProductRequest;
import com.nexbuy.product.dto.ProductResponse;
import com.nexbuy.product.entity.Product;
import com.nexbuy.product.exception.ProductNotFoundException;
import com.nexbuy.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;

	@Override
	public ProductResponse createProduct(ProductRequest request) {
		Product product = new Product();

		product.setName(request.getName());
		product.setDescription(request.getDescription());
		product.setPrice(request.getPrice());
		product.setCategory(request.getCategory());
		product.setStockQuantity(request.getStockQuantity());
		product.setCreatedAt(LocalDateTime.now());

		Product savedProduct = productRepository.save(product);

		return toResponse(savedProduct);
	}

	@Override
	public ProductResponse getProductById(Long id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));

		return toResponse(product);
	}

	@Override
	public List<ProductResponse> getAllProducts() {
		return productRepository.findAll().stream().map(this::toResponse).toList();
	}

	private ProductResponse toResponse(Product product) {
		return new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice(),
				product.getCategory(), product.getStockQuantity(), product.getCreatedAt());
	}

	@Override
	public ProductResponse updateProduct(Long id, ProductRequest request) {

		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));

		product.setName(request.getName());
		product.setDescription(request.getDescription());
		product.setPrice(request.getPrice());
		product.setCategory(request.getCategory());
		product.setStockQuantity(request.getStockQuantity());
		return toResponse(productRepository.save(product));
	}

	@Override
	public void deleteProduct(Long id) {
		Product product = productRepository
						.findById(id)
						.orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
		productRepository.delete(product);
	}

}
