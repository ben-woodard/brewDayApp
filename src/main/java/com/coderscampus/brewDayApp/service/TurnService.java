package com.coderscampus.brewDayApp.service;

import com.coderscampus.brewDayApp.domain.Batch;
import com.coderscampus.brewDayApp.domain.Turn;
import com.coderscampus.brewDayApp.domain.TurnDTO;
import com.coderscampus.brewDayApp.repository.TurnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TurnService {

    private final TurnRepository turnRepo;

    @Autowired
    public TurnService(TurnRepository turnRepo) {
        this.turnRepo = turnRepo;
    }

    public Turn findById(Long turnId) {
        return turnRepo.findById(turnId).orElse(null);
    }

    public void completeTurn(Turn turn) {
        turn.setTurnComplete(true);
        turnRepo.save(turn);
    }

    public void delete(Turn turn) {
        turnRepo.delete(turn);
    }

    public void deleteTurn(Turn turn) {
        Batch batch = turn.getBatch();
        batch.getTurns().remove(turn);
        turnRepo.delete(turn);
    }

    public void createOneBatchTurn(Batch batch, TurnDTO turnDTO) {
        Turn turn = new Turn();
        turn.setTurnNumber(batch.getTurns().size() + 1);
        turn.setTurnComplete(false);
        turn.setRecipeId(batch.getSelectedRecipeId());
        batch.getTurns().add(turn);
        turn.setBatch(batch);
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

    //    public void deleteTurnsFromBatch(Batch batch, int turnDifference) {
//        int i = 0;
//        while(i < turnDifference) {
//            Turn turn = batch.getTurns().get(batch.getTurns().size() - 1);
//            batch.getTurns().remove(turn);
//            turnRepo.delete(turn);
//            i++;
//        }
//    }
}
