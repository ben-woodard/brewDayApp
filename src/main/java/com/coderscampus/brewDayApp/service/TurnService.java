package com.coderscampus.brewDayApp.service;

import com.coderscampus.brewDayApp.domain.Turn;
import com.coderscampus.brewDayApp.repository.TurnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
