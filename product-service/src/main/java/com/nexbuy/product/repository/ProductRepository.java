package com.nexbuy.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nexbuy.product.entity.Category;
import com.nexbuy.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findByNameContainingIgnoreCase(String name);
	List<Product> findByCategory(Category category);
}
