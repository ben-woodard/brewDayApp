package com.coderscampus.brewDayApp.service;

import com.coderscampus.brewDayApp.domain.Recipe;
import com.coderscampus.brewDayApp.domain.User;
import com.coderscampus.brewDayApp.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepo;

    @Autowired
    public RecipeService(RecipeRepository recipeRepo) {
        this.recipeRepo = recipeRepo;
    }

    public Recipe save(Recipe recipe) {
        return recipeRepo.save(recipe);
    }

    public List<Recipe> findAllUserRecipes(User user) {
        List<Recipe> recipes = new ArrayList<>();
        user.getProducts().stream()
                .map(product -> product.getRecipes())
                .forEach(recipes::addAll);
        return recipes;
    }
}
