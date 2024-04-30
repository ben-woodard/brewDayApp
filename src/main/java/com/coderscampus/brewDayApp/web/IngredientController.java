package com.coderscampus.brewDayApp.web;

import com.coderscampus.brewDayApp.domain.Ingredient;
import com.coderscampus.brewDayApp.domain.User;
import com.coderscampus.brewDayApp.service.IngredientService;
import com.coderscampus.brewDayApp.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/inventory")
public class IngredientController {

    private final IngredientService ingredientService;
    private final UserServiceImpl userService;

    @Autowired
    public IngredientController(IngredientService ingredientService, UserServiceImpl userService) {
        this.ingredientService = ingredientService;
        this.userService = userService;
    }

    @GetMapping("{userId}/home")
    public String getUserInventoryHome(@PathVariable Integer userId, ModelMap model) {
        User user = userService.findUserById(userId).orElse(null);
        model.addAttribute("ingredient", new Ingredient());
        model.addAttribute("ingredientsList", ingredientService.findAllIngredientsByUserSorted(user));
        model.addAttribute("user", user);
        return "ingredient/home";
    }

    @PostMapping("{userId}/create")
    public String postCreateIngredient(@ModelAttribute Ingredient ingredient, @PathVariable Integer userId) {
        ingredientService.saveIngredientUserRelationship(ingredient, userId);
        return "redirect:/inventory/{userId}/home";
    }

    @GetMapping("/{userId}/{ingredientId}")
    public String getIngredientInfoById(@PathVariable Long ingredientId, ModelMap model, @PathVariable Integer userId) {
        Ingredient ingredient = ingredientService.findById(ingredientId);
        model.addAttribute("user", userService.findUserById(userId).orElse(null));
        model.addAttribute("ingredient", ingredient);
        model.addAttribute("recipeList", ingredient.getRecipes());
        return "ingredient/update";
    }

    @PostMapping("/{userId}/{ingredientId}")
    public String postUpdateIngredientInfo(@PathVariable Integer userId, @ModelAttribute Ingredient ingredient) {
        ingredientService.saveIngredientUserRelationship(ingredient, userId);
        return "redirect:/inventory/{userId}/home";
    }

    @PostMapping("/{userId}/{ingredientId}/delete")
    public String postDeleteIngredient(@PathVariable Long ingredientId, @PathVariable Integer userId) {
        Ingredient ingredient = ingredientService.findById(ingredientId);
        User user = userService.findUserById(userId).orElse(null);
        ingredientService.delete(ingredient, user);
        return "redirect:/inventory/{userId}/home";
    }

}


