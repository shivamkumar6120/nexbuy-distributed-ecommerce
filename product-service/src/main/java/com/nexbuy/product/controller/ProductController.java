package com.nexbuy.product.controller;

import com.nexbuy.product.service.ProductServiceImpl;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nexbuy.product.dto.ProductRequest;
import com.nexbuy.product.dto.ProductResponse;
import com.nexbuy.product.entity.Category;
import com.nexbuy.product.service.ProductService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {

	private final ProductService productService;

	@PostMapping
	public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {

		return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(request));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProductResponse> getProductId(@PathVariable Long id) {

		return ResponseEntity.ok(productService.getProductById(id));
	}

	@GetMapping
	public ResponseEntity<List<ProductResponse>> getAllProducts() {

		return ResponseEntity.ok(productService.getAllProducts());
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id,
			@Valid @RequestBody ProductRequest request) {
		return ResponseEntity.ok(productService.updateProduct(id, request));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteProduct(@PathVariable Long id) {
		productService.deleteProduct(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/search")
	public ResponseEntity<List<ProductResponse>> searchProducts(@RequestParam(required = false) String q,
			@RequestParam(required = false) Category category) {
		return ResponseEntity.ok(productService.searchProducts(q, category));

	}

	@GetMapping("/{id}/stock")
	public ResponseEntity<ProductResponse> getStock(@PathVariable Long id) {
		return ResponseEntity.ok(productService.getStock(id));
	}

	@PutMapping("/{id}/stock/reduce")
	public ResponseEntity<ProductResponse> reduceStock(@PathVariable Long id, @RequestParam Integer quantity) {
		return ResponseEntity.ok(productService.reduceStock(id, quantity));
	}

	@PutMapping("/{id}/stock/increase")
	public ResponseEntity<ProductResponse> increaseStock(@PathVariable Long id, @RequestParam Integer quantity) {
		return ResponseEntity.ok(productService.increaseStock(id, quantity));
	}
}
