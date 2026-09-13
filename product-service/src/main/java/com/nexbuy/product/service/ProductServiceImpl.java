package com.nexbuy.product.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.nexbuy.product.dto.ProductRequest;
import com.nexbuy.product.dto.ProductResponse;
import com.nexbuy.product.entity.Category;
import com.nexbuy.product.entity.Product;
import com.nexbuy.product.exception.InsufficientStockException;
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
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
		productRepository.delete(product);
	}

	@Override
	public List<ProductResponse> searchProducts(String name, Category category) {

		List<Product> products;
		if (name != null && category != null) {
			products = productRepository.findByNameContainingIgnoreCase(name).stream()
					.filter(product -> product.getCategory() == category).toList();
		} else if (name != null) {
			products = productRepository.findByNameContainingIgnoreCase(name);
		} else if (category != null) {
			products = productRepository.findByCategory(category);
		} else {
			products = productRepository.findAll();
		}
		return products.stream().map(this::toResponse).toList();
	}

	@Override
	public ProductResponse getStock(Long id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
		return toResponse(product);
	}

	@Override
	public ProductResponse reduceStock(Long id, Integer quantity) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
		if (product.getStockQuantity() < quantity) {
			throw new InsufficientStockException("Insufficient stock for product id: " + id);
		}

		product.setStockQuantity(product.getStockQuantity() - quantity);
		return toResponse(productRepository.save(product));
	}

	@Override
	public ProductResponse increaseStock(Long id, Integer quantity) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
		product.setStockQuantity(product.getStockQuantity() + quantity);
		return toResponse(productRepository.save(product));
	}

}
