package com.coderscampus.brewDayApp.web;

import com.coderscampus.brewDayApp.domain.Batch;
import com.coderscampus.brewDayApp.domain.BatchDTO;
import com.coderscampus.brewDayApp.domain.Recipe;
import com.coderscampus.brewDayApp.domain.User;
import com.coderscampus.brewDayApp.service.BatchService;
import com.coderscampus.brewDayApp.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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
        Long defaultRecipeId = batch.getProduct().getDefaultRecipeId();
        model.addAttribute("batch", batch);
        model.addAttribute("user", user);
        model.addAttribute("product", batch.getProduct());
        model.addAttribute("defaultRecipe", recipeService.findById(defaultRecipeId));
        return "batch/start-batch";
    }
}
