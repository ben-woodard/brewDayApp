package com.coderscampus.brewDayApp.web;

import com.coderscampus.brewDayApp.domain.Ingredient;
import com.coderscampus.brewDayApp.domain.Product;
import com.coderscampus.brewDayApp.domain.Recipe;
import com.coderscampus.brewDayApp.domain.User;
import com.coderscampus.brewDayApp.service.IngredientService;
import com.coderscampus.brewDayApp.service.ProductService;
import com.coderscampus.brewDayApp.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/recipes")
public class RecipeController {

    private final UserServiceImpl userService;
    private final ProductService productService;
    private final IngredientService ingredientService;

    @Autowired
    public RecipeController(UserServiceImpl userService, ProductService productService, IngredientService ingredientService) {
        this.userService = userService;
        this.productService = productService;
        this.ingredientService = ingredientService;
    }

    @GetMapping("/{productId}/create")
    public String getCreateNewRecipe(ModelMap model, @PathVariable Long productId) {
        List<Ingredient> ingredients = ingredientService.findAll();
        Product product = productService.findById(productId);
        User user = product.getUser();
        model.put("user", user);
        model.put("ingredients", ingredients);
        model.put("product", product);
        model.put("recipe", new Recipe());
        return "recipe/create";
    }
}
