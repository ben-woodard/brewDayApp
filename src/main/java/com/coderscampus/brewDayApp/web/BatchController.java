package com.coderscampus.brewDayApp.web;

import com.coderscampus.brewDayApp.domain.*;
import com.coderscampus.brewDayApp.service.BatchService;
import com.coderscampus.brewDayApp.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@Controller
@RequestMapping("/batches")
public class BatchController {

    private final BatchService batchService;
    private final RecipeService recipeService;

    @Autowired
    public BatchController(BatchService batchService, RecipeService recipeService) {
        this.batchService = batchService;
        this.recipeService = recipeService;
    }

    @PostMapping("{userId}/create")
    public String postCreateBatch(@ModelAttribute Batch batch, @ModelAttribute BatchDTO batchDTO) {
        batchService.createNewBatch(batch, batchDTO);
        return "redirect:/home/{userId}";
    }

    @GetMapping("/{batchId}/startbatch")
    public String getStartBatch(ModelMap model, @PathVariable Long batchId) {
        Batch batch = batchService.findById(batchId);
        User user = batch.getProduct().getUser();
        Recipe recipe = recipeService.findById(batch.getSelectedRecipeId());
        model.addAttribute("batch", batch);
        model.addAttribute("turn", new Turn());
        model.addAttribute("user", user);
        model.addAttribute("product", batch.getProduct());
        model.addAttribute("recipe", recipe);
        model.addAttribute("ingredientsAndAmounts", recipeService.getMapOfRecipeIngredientsAndAmounts(recipe));
        model.addAttribute("currentDate", LocalDate.now());
        model.addAttribute("batchDTO", new BatchDTO());
        return "batch/start-batch";
    }

    @PostMapping("/{batchId}/update")
    public String postUpdateBatchInformation(@PathVariable Long batchId, @ModelAttribute Batch batch) {
        batchService.updateBatch(batch, batchId);
        return "redirect:/batches/" + batchId + "/startbatch";
    }

    @PostMapping("/{batchId}/delete")
    public String postDeleteBatch(@PathVariable Long batchId) {
        Batch batch = batchService.findById(batchId);
        Integer userId = batch.getProduct().getUser().getId();
        batchService.deleteBatch(batch);
        return "redirect:/home/" + userId;
    }

    @PostMapping("/{batchId}/createturn")
    public String postCreateTurn(@PathVariable Long batchId, @ModelAttribute Turn turn) {
        
    }
}
