package com.coderscampus.brewDayApp.service;

import com.coderscampus.brewDayApp.domain.*;
import com.coderscampus.brewDayApp.repository.BatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Comparator;
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

    public List<Batch> findAllIncompleteBatchesByUserId(Integer userId) {
        User user = userService.findUserById(userId).orElse(null);
        return user.getProducts().stream()
                .flatMap(product -> product.getBatches().stream())
                .filter(batch -> batch.getBatchComplete() == false)
                .sorted(Comparator.comparing(Batch::getStartDate))
                .collect(Collectors.toList());
    }

    public Batch createNewBatch(Batch batch, BatchDTO batchDTO) {
        Product product = productService.findById(batchDTO.getProductId());
        batch.setProduct(product);
        batch.setBatchComplete(false);
        batch.setTurnsComplete(false);
        batch.setSelectedRecipeId(product.getDefaultRecipeId());
        product.getBatches().add(batch);
        return batchRepo.save(batch);
    }

    public List<Batch> findTodaysTasks(List<Batch> batches) {
        LocalDate date = LocalDate.now();
        return batches.stream()
                .filter(batch -> (batch.getStartDate()).equals(date) || (batch.getEndDate()).equals(date))
                .sorted(Comparator.comparing(Batch::getBatchNumber))
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

    public Batch updateBatch(Batch batch, Long batchId) {
        Batch savedBatch = batchRepo.findById(batchId).orElse(null);
        savedBatch.setSelectedRecipeId(batch.getSelectedRecipeId());
        savedBatch.getTurns().forEach(turn -> {
            if (!turn.getTurnComplete()) {
                turn.setRecipeId(batch.getSelectedRecipeId());
            }
        });
        savedBatch.setTankName(batch.getTankName());
        savedBatch.setBatchNumber(batch.getBatchNumber());
        batch.setTurnsComplete(savedBatch.getTurnsComplete());
        batch.setBatchComplete(savedBatch.getBatchComplete());
        return batchRepo.save(savedBatch);
    }

    public void deleteBatch(Batch batch) {
        Product product = batch.getProduct();
        product.getBatches().remove(batch);
        batch.getTurns().forEach(turn -> {
            batch.getTurns().remove(turn);
            turnService.delete(turn);
        });
        batchRepo.delete(batch);
    }

    public void completeBatch(Batch batch) {
        batch.setBatchComplete(true);
        batchRepo.save(batch);
    }

}
