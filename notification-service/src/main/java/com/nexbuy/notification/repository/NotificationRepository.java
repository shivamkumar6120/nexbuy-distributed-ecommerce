package com.nexbuy.notification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nexbuy.notification.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

	List<Notification> findByUserEmail(String userEmail);
}