package com.coderscampus.brewDayApp.web;

import com.coderscampus.brewDayApp.domain.User;
import com.coderscampus.brewDayApp.service.OrderService;
import com.coderscampus.brewDayApp.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String getUserOrdersHomePage(@PathVariable Integer userId) {
        User user = userService.findUserById(userId).orElse(null);
        if(user == null) {
            return "redirect:/signin";
        }
        return "order/home";
    }

}
