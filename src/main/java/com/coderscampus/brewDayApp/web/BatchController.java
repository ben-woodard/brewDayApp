package com.coderscampus.brewDayApp.web;

import com.coderscampus.brewDayApp.domain.*;
import com.coderscampus.brewDayApp.service.BatchService;
import com.coderscampus.brewDayApp.service.RecipeService;
import com.coderscampus.brewDayApp.service.TurnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/batches")
public class BatchController {

    private final BatchService batchService;
    private final RecipeService recipeService;
    private final TurnService turnService;

    @Autowired
    public BatchController(BatchService batchService, RecipeService recipeService, TurnService turnService) {
        this.batchService = batchService;
        this.recipeService = recipeService;
        this.turnService = turnService;
    }

    @PostMapping("{userId}/create")
    public String postCreateBatch(@ModelAttribute Batch batch, @ModelAttribute BatchDTO batchDTO) {
        batchService.createNewBatch(batch, batchDTO);
        turnService.addBatchTurns(batch, batch.getNumberOfTurns(), null);
        return "redirect:/home/{userId}";
    }

    @GetMapping("/{batchId}/startbatch")
    public String getStartBatch(ModelMap model, @PathVariable Long batchId) {
        Batch batch = batchService.findById(batchId);
        User user = batch.getProduct().getUser();
        Recipe recipe = recipeService.findById(batch.getSelectedRecipeId());
        model.addAttribute("batch", batch);
        model.addAttribute("user", user);
        model.addAttribute("product", batch.getProduct());
        model.addAttribute("recipe", recipe);
        model.addAttribute("ingredientsAndAmounts", recipeService.getMapOfRecipeIngredientsAndAmounts(recipe));
        model.addAttribute("currentDate", LocalDate.now());
        model.addAttribute("turnDTO", new TurnDTO());
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
    public String postCreateTurn(@PathVariable Long batchId, @ModelAttribute TurnDTO turnDTO) {
        Batch batch = batchService.findById(batchId);
        turnService.addBatchTurns(batch, 1, Optional.ofNullable(turnDTO));
        return "redirect:/batches/" + batch.getBatchId() + "/startbatch";
    }
}
