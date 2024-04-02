package com.coderscampus.brewDayApp.web;

import com.coderscampus.brewDayApp.domain.Ingredient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/inventory")
public class IngredientController {

    @GetMapping("/create")
    public String getCreateIngredient(ModelMap model) {
        model.put("ingredient", new Ingredient());
        return"ingredient/create";
    }
}
