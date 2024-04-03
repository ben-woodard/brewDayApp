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
        model.addAttribute("ingredientsList", user.getIngredients());
        model.addAttribute("user", user);
        return "ingredient/home";
    }

    @GetMapping("{userId}/create")
    public String getCreateIngredient(ModelMap model) {
        model.addAttribute("ingredient", new Ingredient());
        return"ingredient/create";
    }

    @PostMapping("{userId}/create")
    public String postCreateIngredient(@ModelAttribute Ingredient ingredient, @PathVariable Integer userId) {
        Ingredient savedIngredient = ingredientService.saveIngredientUserRelationship(ingredient, userId);
        return "redirect:/inventory/{userId}/" + savedIngredient.getIngredientId();
    }

    @GetMapping("/{userId}/{ingredientId}")
    public String getIngredientInfoById(@PathVariable Long ingredientId, ModelMap model) {
        model.addAttribute("ingredient", ingredientService.findById(ingredientId));
        return "ingredient/create";
    }

    @PostMapping("/{userId}/{ingredientId}")
    public String postUpdateIngredientInfo(@ModelAttribute Ingredient ingredient, @PathVariable Integer userId) {
        ingredientService.saveIngredientUserRelationship(ingredient, userId);
        return "redirect:/inventory/"+ingredient.getIngredientId();
    }
}


