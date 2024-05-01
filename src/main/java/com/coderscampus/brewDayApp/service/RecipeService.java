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

    public Recipe saveRecipeProductRelationship(Recipe recipe, Product product) {
        Recipe savedRecipe = recipeRepo.save(recipe);
        product.getRecipes().add(savedRecipe);
        if(product.getDefaultRecipeId() == null) {
            product.setDefaultRecipeId(savedRecipe.getRecipeId());
        }
        savedRecipe.setProduct(product);
        productService.save(product);
        return savedRecipe;
    }

    public Recipe updateRecipeName(Recipe recipe, Long recipeId) {
        Recipe savedRecipe = recipeRepo.findById(recipeId).orElse(null);
        savedRecipe.setRecipeName(recipe.getRecipeName());
        return save(savedRecipe);
    }

    public Recipe saveRecipeIngredientRelationship(RecipeDTO recipeDTO, Long recipeId) {
        Recipe recipe = findById(recipeId);
        Ingredient ingredient = ingredientService.findById(recipeDTO.getIngredientId());
        ingredient.getRecipes().add(recipe);
        recipe.getIngredients().add(ingredient);
        recipe.getIngredientsToRemove().put(ingredient.getIngredientId(), recipeDTO.getAmount());
        return recipeRepo.save(recipe);
    }

    public Map<Ingredient, Double> getMapOfRecipeIngredientsAndAmounts(Recipe recipe) {
        Map<Ingredient, Double> ingredientAmounts = recipe.getIngredientsToRemove().entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> ingredientService.findById(entry.getKey()),
                        Map.Entry::getValue
                ));

        List<Map.Entry<Ingredient, Double>> sortedEntries = new ArrayList<>(ingredientAmounts.entrySet());
        sortedEntries.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        Map<Ingredient, Double> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<Ingredient, Double> entry : sortedEntries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    public List<Recipe> findAllRecipesByUser(User user) {
        return user.getProducts().stream()
                .flatMap(product -> product.getRecipes().stream())
                .collect(Collectors.toList());
    }

    public void delete(Long recipeId) {
        Recipe recipe = findById(recipeId);
        Product product = recipe.getProduct();
        product.getRecipes().remove(recipe);
        if(product.getDefaultRecipeId() == recipeId) {
            product.setDefaultRecipeId(null);
        }
        recipe.getIngredients().removeAll(recipe.getIngredients());
        recipeRepo.delete(recipe);
    }

    public void removeIngredientFromRecipe(Recipe recipe, Ingredient ingredient) {
        recipe.getIngredients().remove(ingredient);
        recipe.getIngredientsToRemove().remove(ingredient.getIngredientId());
        ingredient.getRecipes().remove(recipe);
        ingredientService.save(ingredient);
        recipeRepo.save(recipe);
    }
}
