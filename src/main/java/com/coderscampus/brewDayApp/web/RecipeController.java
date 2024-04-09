package com.coderscampus.brewDayApp.web;

import com.coderscampus.brewDayApp.domain.*;
import com.coderscampus.brewDayApp.service.ProductService;
import com.coderscampus.brewDayApp.service.RecipeService;
import com.coderscampus.brewDayApp.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/recipes")
public class RecipeController {

    private final UserServiceImpl userService;
    private final ProductService productService;
    private final RecipeService recipeService;


    @Autowired
    public RecipeController(UserServiceImpl userService, ProductService productService, RecipeService recipeService) {
        this.userService = userService;
        this.productService = productService;
        this.recipeService = recipeService;
    }

    @GetMapping("/{userId}/home")
    public String getRecipesHome(ModelMap model, @PathVariable Integer userId) {
        User user = userService.findUserById(userId).orElse(null);
        model.addAttribute("user", user);
        model.addAttribute("recipesList", recipeService.findAllRecipesByUser(user));
        model.addAttribute("productsList", user.getProducts());
        model.addAttribute("recipe", new Recipe());
        model.addAttribute("recipeDTO", new RecipeDTO());
        return "recipe/home";
    }

    @PostMapping("/create")
    public String createNewRecipeFromRecipeHome(@ModelAttribute RecipeDTO recipeDTO, @ModelAttribute Recipe recipe) {
        Recipe savedRecipe = recipeService.saveRecipeProductRelationship(recipe, recipeDTO.getProductId());
        return "redirect:/recipes/" + recipe.getProduct().getProductId() + "/" + savedRecipe.getRecipeId();
    }

    @PostMapping("/{productId}/create")
    public String createNewRecipeFromRecipeCreate(@ModelAttribute Recipe recipe, @PathVariable Long productId) {
        Recipe savedRecipe = recipeService.saveRecipeProductRelationship(recipe, productId);
        return "redirect:/recipes/{productId}/" + savedRecipe.getRecipeId();
    }

    @GetMapping("/{productId}/{recipeId}")
    public String getRecipeInformation(Model model, @PathVariable Long recipeId, @PathVariable Long productId) {
        Product product = productService.findById(productId);
        Recipe recipe = recipeService.findById(recipeId);
        User user = product.getUser();
        model.addAttribute("recipeDTO", new RecipeDTO());
        model.addAttribute("ingredients", user.getIngredients());
        model.addAttribute("ingredientAmountMap", recipeService.getMapOfRecipeIngredientsAndAmounts(recipe));
        model.addAttribute("product", product);
        model.addAttribute("user", user);
        model.addAttribute("recipe", recipe);
        return "recipe/update";
    }

    @PostMapping("/{productId}/{recipeId}")
    public String postUpdateRecipe(@ModelAttribute Recipe recipe, @PathVariable Long recipeId) {
        recipeService.updateRecipeName(recipe, recipeId);
        return "redirect:/recipes/{productId}/{recipeId}";
    }

    @PostMapping("/{recipeId}/addingredient")
    public String postAddIngredientToRecipe(@ModelAttribute RecipeDTO recipeDTO, @PathVariable Long recipeId) {
        Recipe recipe = recipeService.saveRecipeIngredientRelationship(recipeDTO, recipeId);
        Product product = recipe.getProduct();
        return "redirect:/recipes/" + product.getProductId() + "/" + recipe.getRecipeId();
    }

    @PostMapping("/{productId}/{recipeId}/delete")
    public String postDeleteRecipe(@PathVariable Long productId, @PathVariable Long recipeId) {
        Product product = productService.findById(productId);
        recipeService.delete(recipeId);
        return "redirect:/products/" + product.getUser().getId() + "/{productId}";
    }

}
