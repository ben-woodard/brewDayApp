package com.coderscampus.brewDayApp.web;

import com.coderscampus.brewDayApp.domain.Batch;
import com.coderscampus.brewDayApp.domain.BatchDTO;
import com.coderscampus.brewDayApp.domain.Product;
import com.coderscampus.brewDayApp.domain.User;
import com.coderscampus.brewDayApp.service.BatchService;
import com.coderscampus.brewDayApp.service.ProductService;
import com.coderscampus.brewDayApp.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;

@Controller
public class UserController {

    private final UserServiceImpl userService;
    private final ProductService productService;
    private final BatchService batchService;

    @Autowired
    public UserController(UserServiceImpl userService, ProductService productService, BatchService batchService) {
        this.userService = userService;
        this.productService = productService;
        this.batchService = batchService;
    }

    @GetMapping("/home/{userId}")
    public String getUserHomePage(@PathVariable Integer userId, ModelMap model) {
        User user = userService.findUserById(userId).orElse(null);
        if(user == null) {
            return "redirect:/signin";
        }
        List<Batch> batches = batchService.findAllIncompleteBatchesByUserId(userId);
        List<Batch> todaysBatches = batchService.findTodaysTasks(batches);
        List<Product> products = user.getProducts();
        model.addAttribute("batch", new Batch());
        model.addAttribute("batchDTO", new BatchDTO());
        model.addAttribute("todaysBatches", todaysBatches);
        model.addAttribute("batches", batches);
        model.addAttribute("products", products);
        model.addAttribute("user", user);
        model.addAttribute("currentDate", LocalDate.now());
        return "user/home";
    }
}
