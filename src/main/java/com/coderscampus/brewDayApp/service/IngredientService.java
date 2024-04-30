package com.coderscampus.brewDayApp.service;

import com.coderscampus.brewDayApp.domain.Ingredient;
import com.coderscampus.brewDayApp.domain.User;
import com.coderscampus.brewDayApp.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepo;
    private final UserServiceImpl userService;

    @Autowired
    public IngredientService(IngredientRepository ingredientRepo, UserServiceImpl userService) {
        this.ingredientRepo = ingredientRepo;
        this.userService = userService;
    }

    public Ingredient save(Ingredient ingredient) {
        return ingredientRepo.save(ingredient);
    }

    public Ingredient findById(Long ingredientId) {
        return ingredientRepo.findById(ingredientId).orElse(null);
    }

    public Ingredient saveIngredientUserRelationship(Ingredient ingredient, Integer userId) {
        User user = userService.findUserById(userId).orElse(null);
        ingredient.setUser(user);
        user.getIngredients().add(ingredient);
        return ingredientRepo.save(ingredient);
    }

    public void delete(Ingredient ingredient, User user) {
        userService.removeIngredientFromUser(user, ingredient);
        ingredient.getRecipes().stream()
                .forEach(recipe -> recipe.getIngredients().remove(ingredient));
        ingredientRepo.delete(ingredient);
    }

    public List<Ingredient> findAllIngredientsByUserSorted(User user) {
        return user.getIngredients().stream()
                .sorted(Comparator.comparing(ingredient -> ingredient.getIngredientName()))
                .collect(Collectors.toList());
    }

}
