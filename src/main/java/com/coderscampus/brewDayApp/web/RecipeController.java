package com.coderscampus.brewDayApp.web;

import com.coderscampus.brewDayApp.domain.Ingredient;
import com.coderscampus.brewDayApp.domain.Product;
import com.coderscampus.brewDayApp.domain.Recipe;
import com.coderscampus.brewDayApp.domain.User;
import com.coderscampus.brewDayApp.service.IngredientService;
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
@RequestMapping("/recipes")
public class RecipeController {

    private final UserServiceImpl userService;
    private final ProductService productService;
    private final IngredientService ingredientService;
    private final RecipeService recipeService;


    @Autowired
    public RecipeController(UserServiceImpl userService, ProductService productService, IngredientService ingredientService, RecipeService recipeService) {
        this.userService = userService;
        this.productService = productService;
        this.ingredientService = ingredientService;
        this.recipeService = recipeService;
    }

    @GetMapping("/{userId}/home")
    public String getRecipesHome(ModelMap model, @PathVariable Integer userId) {
        User user = userService.findUserById(userId).orElse(null);
        model.addAttribute("user", user);
        model.addAttribute("recipesList", recipeService.findAllUserRecipes(user));
        return "recipe/home";
    }

//    @GetMapping("/{userId}/create")
//    public String createNewRecipeFromRecipeHome(ModelMap model, @PathVariable Integer userId) {
//        User user = userService.findUserById(userId).orElse(null);
//        model.addAttribute("product", new Product());
//        model.addAttribute("user", user);
//        model.addAttribute("productsList", user.getProducts());
//        model.addAttribute("recipe", new Recipe());
//        return "recipe/create";
//    }

    @GetMapping("/{productId}/create")
    public String getCreateNewRecipe(ModelMap model, @PathVariable Long productId) {
        List<Ingredient> ingredients = ingredientService.findAll();
        Product product = productService.findById(productId);
        User user = product.getUser();
        model.addAttribute("user", user);
        model.addAttribute("ingredients", ingredients);
        model.addAttribute("productsList", user.getProducts());
        model.addAttribute("product", product);
        model.addAttribute("recipe", new Recipe());
        return "recipe/create";
    }

//    @PostMapping("/{productId}/create")
}
