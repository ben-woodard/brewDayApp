package com.coderscampus.brewDayApp.web;

import com.coderscampus.brewDayApp.domain.Batch;
import com.coderscampus.brewDayApp.domain.Turn;
import com.coderscampus.brewDayApp.service.BatchService;
import com.coderscampus.brewDayApp.service.TurnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/turns")
public class TurnController {

    private final TurnService turnService;
    private final BatchService batchService;

    @Autowired
    public TurnController(TurnService turnService, BatchService batchService) {
        this.turnService = turnService;
        this.batchService = batchService;
    }

    @PostMapping("/{turnId}/complete")
    public String postCompleteTurn(@PathVariable Long turnId) {
        Turn turn = turnService.findById(turnId);
        Batch batch = turn.getBatch();
        turnService.completeTurn(turn);
        batchService.removeIngredientsFromInventory(turn);
        batchService.checkForAllTurnsComplete(batch);
        return "redirect:/batches/" + batch.getBatchId() + "/startbatch";
    }
}
