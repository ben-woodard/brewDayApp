package com.coderscampus.brewDayApp.repository;

import com.coderscampus.brewDayApp.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Long, Order> {
}
