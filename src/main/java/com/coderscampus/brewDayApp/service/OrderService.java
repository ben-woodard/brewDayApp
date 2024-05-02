package com.coderscampus.brewDayApp.service;

import com.coderscampus.brewDayApp.domain.Order;
import com.coderscampus.brewDayApp.domain.User;
import com.coderscampus.brewDayApp.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepo;
    private final UserServiceImpl userService;

    @Autowired
    public OrderService(OrderRepository orderRepo, UserServiceImpl userService) {
        this.orderRepo = orderRepo;
        this.userService = userService;
    }

    public Order save(Order order) {
        return orderRepo.save(order);
    }

    public List<Order> findAllOpenOrdersByUser(User user) {
        return user.getOrders().stream()
                .filter(order -> order.getOrderReceived() == false)
                .collect(Collectors.toList());
    }

    public List<Order> findAllClosedOrdersByUser(User user) {
        return user.getOrders().stream()
                .filter(order -> order.getOrderReceived() == true)
                .collect(Collectors.toList());
    }

    public Order findById(Long orderId) {
        return orderRepo.findById(orderId).orElse(null);
    }

    public Order saveUserOrderRelationship(User user, Order order) {
        order.setOrderReceived(false);
        order.setUser(user);
        Order savedOrder = orderRepo.save(order);
        user.getOrders().add(savedOrder);
        userService.save(user);
        return savedOrder;
    }
}
