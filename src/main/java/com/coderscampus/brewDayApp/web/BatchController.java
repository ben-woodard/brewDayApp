package com.coderscampus.brewDayApp.web;

import com.coderscampus.brewDayApp.domain.Batch;
import com.coderscampus.brewDayApp.domain.BatchDTO;
import com.coderscampus.brewDayApp.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/batches")
public class BatchController {

    private final BatchService batchService;

    @Autowired
    public BatchController(BatchService batchService) {
        this.batchService = batchService;
    }

    @PostMapping("{userId}/create")
    public String postCreateBatch(@ModelAttribute Batch batch, @ModelAttribute BatchDTO batchDTO) {
        batchService.createNewBatch(batch, batchDTO);
        return "redirect:/home/{userId}";
    }

    @GetMapping("/{batchId}/startbatch")
    public String getStartBatch(ModelMap model, @PathVariable Long batchId) {
        Batch batch = batchService.findById(batchId);
        model.addAttribute("batch", batch);
        return "batch/start-batch";
    }
}
