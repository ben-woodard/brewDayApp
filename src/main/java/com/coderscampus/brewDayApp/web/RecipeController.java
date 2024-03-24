package com.coderscampus.brewDayApp.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/recipes")
public class RecipeController {

    @GetMapping("/{userId}/create")
    public String getCreateNewRecipe(ModelMap model) {
        return "recipe/create";
    }
}
