package com.nexbuy.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nexbuy.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	List<Order> findByUserEmail(String email);
}
