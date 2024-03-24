package com.coderscampus.brewDayApp.service;

import com.coderscampus.brewDayApp.domain.Ingredient;
import com.coderscampus.brewDayApp.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepo;

    @Autowired
    public IngredientService(IngredientRepository ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }

    public List<Ingredient> findAll() {
        return ingredientRepo.findAll();
    }
}
