package com.coderscampus.brewDayApp.web;

import com.coderscampus.brewDayApp.domain.Product;
import com.coderscampus.brewDayApp.domain.Recipe;
import com.coderscampus.brewDayApp.domain.RecipeDTO;
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
        model.addAttribute("product", new Product());
        model.addAttribute("user", user);
        model.addAttribute("products", user.getProducts());
        return "product/home";
    }

    @PostMapping("/{userId}/create")
    public String postCreateProductByName(@ModelAttribute Product product, @PathVariable Integer userId) {
        Product dbProduct = productService.createProductUserRelationship(product, userId);
        return "redirect:/products/{userId}/" + dbProduct.getProductId();
    }

    @GetMapping("/{userId}/{productId}")
    public String getProductInformation(ModelMap model, @PathVariable Long productId, @PathVariable Integer userId) {
        User user = userService.findUserById(userId).orElse(null);
        Product product = productService.findById(productId);
        model.addAttribute("recipe", new Recipe());
        model.addAttribute("product", product);
        model.addAttribute("user", user);
        model.addAttribute("recipeDTO", new RecipeDTO());
        if (product.getDefaultRecipeId() != null) {
            Recipe defaultRecipe = recipeService.findById(product.getDefaultRecipeId());
            model.addAttribute("defaultRecipe", defaultRecipe);
        }
        return "product/create";
    }

    @PostMapping("{productId}/delete")
    public String postProductDelete(@PathVariable Long productId) {
        Product product = productService.findById(productId);
        productService.delete(product);
        return "redirect:/products/" + product.getUser().getId();
    }

    @PostMapping("{productId}/setdefaultrecipe")
    public String setDefaultRecipe(@PathVariable Long productId, @ModelAttribute RecipeDTO recipeDTO) {
        Product savedProduct = productService.findById(productId);
        User user = savedProduct.getUser();
        productService.setDefaultRecipe(savedProduct, recipeDTO);
        return "redirect:/products/" + user.getId() + "/{productId}";
    }

}
