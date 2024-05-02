package com.coderscampus.brewDayApp.web;

import com.coderscampus.brewDayApp.domain.Order;
import com.coderscampus.brewDayApp.domain.User;
import com.coderscampus.brewDayApp.service.OrderService;
import com.coderscampus.brewDayApp.service.UserServiceImpl;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final UserServiceImpl userService;
    private final OrderService orderService;

    @Autowired
    public OrderController(UserServiceImpl userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping("/{userId}/home")
    public String getUserOrdersHomePage(@PathVariable Integer userId, ModelMap model) {
        User user = userService.findUserById(userId).orElse(null);
        if(user == null) {
            return "redirect:/signin";
        }
        List<Order> fuck = user.getOrders();
        model.addAttribute("user", user);
        model.addAttribute("newOrder", new Order());
        model.addAttribute("openOrders", user.getOrders());
        model.addAttribute("openOrders", orderService.findAllClosedOrdersByUser(user));
        return "order/home";
    }

    @GetMapping("/{userId}/{orderId}")
    public String getOrderInformation(@PathVariable Integer userId, @PathVariable Long orderId, ModelMap model) {
        User user = userService.findUserById(userId).orElse(null);
        Order order = orderService.findById(orderId);
        return "order/update";
    }

    @PostMapping("/{userId}/create")
    public String postCreateNewOrder(@PathVariable Integer userId, @ModelAttribute Order newOrder) {
        User user = userService.findUserById(userId).orElse(null);
        Order savedOrder = orderService.saveUserOrderRelationship(user, newOrder);
        return "redirect:/orders/" + user.getId() + "/" + savedOrder.getOrderId();
    }

}
