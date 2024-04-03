package com.coderscampus.brewDayApp.web;

import com.coderscampus.brewDayApp.domain.Product;
import com.coderscampus.brewDayApp.domain.Recipe;
import com.coderscampus.brewDayApp.domain.User;
import com.coderscampus.brewDayApp.service.ProductService;
import com.coderscampus.brewDayApp.service.RecipeService;
import com.coderscampus.brewDayApp.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final UserServiceImpl userService;
    private final ProductService productService;
    private final RecipeService recipeService;

    @Autowired
    public ProductController(UserServiceImpl userService, ProductService productService, RecipeService recipeService) {
        this.userService = userService;
        this.productService = productService;
        this.recipeService = recipeService;
    }

    @GetMapping("/{userId}")
    public String getUserProducts(ModelMap model, @PathVariable Integer userId) {
        User user = userService.findUserById(userId).orElse(null);
        if (user == null) {
            return "redirect:/signin";
        }
        model.put("user", user);
        model.put("products", user.getProducts());
        return "product/home";
    }

    @GetMapping("/{userId}/create")
    public String getCreateProduct(ModelMap model, @PathVariable Integer userId) {
        User user = userService.findUserById(userId).orElse(null);
        model.put("recipe", new Recipe());
        model.put("product", new Product());
        return "product/create";
    }

    @PostMapping("/{userId}/create")
    public String postCreateProductByName(@ModelAttribute Product product, @PathVariable Integer userId) {
        Product dbProduct = productService.createProductUserRelationship(product, userId);
        return "redirect:/products/{userId}/"+dbProduct.getProductId();
    }

    @GetMapping("/{userId}/{productId}")
    public String getProductInformation (ModelMap model, @PathVariable Long productId, @PathVariable Integer userId) {
        User user = userService.findUserById(userId).orElse(null);
        model.addAttribute("product", productService.findById(productId));
        model.addAttribute("user", user);
        return "product/create";
    }


}
