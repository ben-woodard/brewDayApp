package com.coderscampus.brewDayApp.service;

import com.coderscampus.brewDayApp.domain.Batch;
import com.coderscampus.brewDayApp.domain.Recipe;
import com.coderscampus.brewDayApp.domain.Turn;
import com.coderscampus.brewDayApp.domain.TurnDTO;
import com.coderscampus.brewDayApp.repository.TurnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class TurnService {

    private final TurnRepository turnRepo;
    private final RecipeService recipeService;

    @Autowired
    public TurnService(TurnRepository turnRepo, RecipeService recipeService) {
        this.turnRepo = turnRepo;
        this.recipeService = recipeService;
    }

    public Turn findById(Long turnId) {
        return turnRepo.findById(turnId).orElse(null);
    }

    public void delete(Turn turn) {
        turnRepo.delete(turn);
    }

    public void completeTurn(Turn turn) {
        turn.setTurnComplete(true);
        turnRepo.save(turn);
    }

    public void deleteTurn(Turn turn) {
        Batch batch = turn.getBatch();
        batch.getTurns().remove(turn);
        renumberBatchTurns(batch);
        turnRepo.delete(turn);
    }

    private void renumberBatchTurns(Batch batch) {
        AtomicInteger i = new AtomicInteger();
        batch.getTurns().forEach(item -> {
            item.setTurnNumber(i.get() + 1);
            i.getAndIncrement();
        });
    }

    public List<Turn> getAllOrganizedTurns(Batch batch) {
        return batch.getTurns().stream()
                .sorted(Comparator.comparingInt(Turn::getTurnNumber))
                .collect(Collectors.toList());
    }

    public void addBatchTurns(Batch batch, Integer batchTurns, Optional<TurnDTO> turnDTO) {
        int i = 0;
        while (i < batchTurns) {
            Turn turn = new Turn();
            turn.setTurnNumber(batch.getTurns().size() + 1);
            turn.setTurnComplete(false);
            if (turnDTO != null) {
                turn.setRecipeId(turnDTO.get().getRecipeId());
            } else {
                turn.setRecipeId(batch.getSelectedRecipeId());
            }
            batch.getTurns().add(turn);
            turn.setBatch(batch);
            turnRepo.save(turn);
            i++;
        }
    }

}
