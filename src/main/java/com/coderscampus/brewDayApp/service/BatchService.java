package com.coderscampus.brewDayApp.service;

import com.coderscampus.brewDayApp.domain.*;
import com.coderscampus.brewDayApp.repository.BatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BatchService {

    private final UserServiceImpl userService;
    private final BatchRepository batchRepo;
    private final ProductService productService;
    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final TurnService turnService;

    @Autowired
    public BatchService(UserServiceImpl userService, BatchRepository batchRepo, ProductService productService, RecipeService recipeService, IngredientService ingredientService, TurnService turnService) {
        this.userService = userService;
        this.batchRepo = batchRepo;
        this.productService = productService;
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.turnService = turnService;
    }

    public Batch save(Batch batch) {
        return batchRepo.save(batch);
    }

    public List<Batch> findAllByUserId(Integer userId) {
        User user = userService.findUserById(userId).orElse(null);
        return user.getProducts().stream()
                .flatMap(product -> product.getBatches().stream())
                .collect(Collectors.toList());
    }

    public Batch createNewBatch(Batch batch, BatchDTO batchDTO) {
        Product product = productService.findById(batchDTO.getProductId());
        batch.setProduct(product);
        batch.setBatchComplete(false);
        batch.setTurnsComplete(false);
        batch.setSelectedRecipeId(product.getDefaultRecipeId());
        createBatchTurns(batch, batch.getNumberOfTurns());
        product.getBatches().add(batch);
        return batchRepo.save(batch);
    }

    private void createBatchTurns(Batch batch, Integer batchTurns) {
        int i = 0;
        while (i < batchTurns) {
            Turn turn = new Turn();
            turn.setTurnNumber(batch.getTurns().size() + 1);
            turn.setTurnComplete(false);
            turn.setRecipeId(batch.getProduct().getDefaultRecipeId());
            batch.getTurns().add(turn);
            turn.setBatch(batch);
            i++;
        }
    }

    private void adjustBatchTurns(Batch batch, Batch savedBatch) {
        if (batch.getNumberOfTurns() > savedBatch.getNumberOfTurns()) {
            int turnDifference = batch.getNumberOfTurns() - savedBatch.getNumberOfTurns();
            createBatchTurns(batch, turnDifference);
        } else {
            int turnDifference = savedBatch.getNumberOfTurns() - batch.getNumberOfTurns();
            turnService.deleteTurnsFromBatch(batch, turnDifference);
        }
    }

    public List<Batch> findTodaysBatches(List<Batch> batches) {
        LocalDate date = LocalDate.now();
        return batches.stream()
                .filter(batch -> (batch.getStartDate()).equals(date))
                .collect(Collectors.toList());
    }

    public Batch findById(Long batchId) {
        return batchRepo.findById(batchId).orElse(null);
    }

    public void removeIngredientsFromInventory(Turn turn) {
        Recipe recipe = recipeService.findById(turn.getRecipeId());
        recipe.getIngredientsToRemove().entrySet().stream()
                .forEach(element -> {
                    Ingredient ingredient = ingredientService.findById(element.getKey());
                    Double amountToRemove = element.getValue();
                    ingredient.setAmountInStock(ingredient.getAmountInStock() - amountToRemove);
                    ingredientService.save(ingredient);
                });
    }

    public void checkForAllTurnsComplete(Batch batch) {
        boolean allTurnsComplete = batch.getTurns().stream().allMatch(Turn::getTurnComplete);
        if (allTurnsComplete) {
            batch.setTurnsComplete(true);
            batchRepo.save(batch);
        }
    }

    public void updateBatch(Batch batch, Long batchId) {
        Batch savedBatch = batchRepo.findById(batchId).orElse(null);
        batch.setTurnsComplete(savedBatch.getTurnsComplete());
        batch.setBatchComplete(savedBatch.getBatchComplete());
        batch.setProduct(savedBatch.getProduct());
        if(batch.getNumberOfTurns() != savedBatch.getNumberOfTurns()) {
            adjustBatchTurns(batch, savedBatch);
        }
        batchRepo.save(batch);
    }
}
