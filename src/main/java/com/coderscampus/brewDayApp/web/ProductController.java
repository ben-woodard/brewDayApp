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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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
//        List<Recipe> recipes = recipeService.findAllUserRecipes(user);

        Product product = new Product();
        Recipe recipe = new Recipe();
        recipe.setRecipeName("TestRecipe");
        recipeService.save(recipe);
        productService.save(product);
        product.getRecipes().add(recipe);
        recipe.setProduct(product);
        model.put("user", user);
        model.put("product", product);
        return "product/create";
    }

}
