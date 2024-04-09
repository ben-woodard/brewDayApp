package com.coderscampus.brewDayApp.service;

import com.coderscampus.brewDayApp.domain.*;
import com.coderscampus.brewDayApp.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepo;
    private final ProductService productService;
    private final IngredientService ingredientService;

    @Autowired
    public RecipeService(RecipeRepository recipeRepo, ProductService productService, IngredientService ingredientService) {
        this.recipeRepo = recipeRepo;
        this.productService = productService;
        this.ingredientService = ingredientService;
    }

    public Recipe save(Recipe recipe) {
        return recipeRepo.save(recipe);
    }

    public Recipe findById(Long recipeId) {
        return recipeRepo.findById(recipeId).orElse(null);
    }

    public List<Recipe> findAllRecipesByUser(User user) {
        return user.getProducts().stream()
                .flatMap(product -> product.getRecipes().stream())
                .collect(Collectors.toList());
    }

    public Recipe createRecipeProductRelationship(Recipe recipe, Long productId) {
        Product product = productService.findById(productId);
        product.getRecipes().add(recipe);
        recipe.setProduct(product);
        return save(recipe);
    }

    public Recipe createRecipeIngredientRelationship(RecipeDTO recipeDTO, Long recipeId) {
        Recipe recipe = recipeRepo.findById(recipeId).orElse(null);
        Ingredient ingredient = ingredientService.findById(recipeDTO.getIngredientId());
        ingredient.getRecipes().add(recipe);
        recipe.getIngredients().add(ingredient);
        recipe.getIngredientsToRemove().put(ingredient.getIngredientId(), recipeDTO.getAmount());
        return save(recipe);
    }

    public Map<Ingredient, Double> getMapOfRecipeIngredientsAndAmounts(Recipe recipe) {
       return recipe.getIngredientsToRemove().entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> ingredientService.findById(entry.getKey()),
                        Map.Entry::getValue
                ));
    }

}
