package com.coderscampus.brewDayApp.domain;

import jakarta.persistence.*;

@Entity
public class Turn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long turnId;
    private Integer turnNumber;
    private Boolean turnComplete;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_id")
    private Batch batch;

    public Turn() {
    }

    public Integer getTurnNumber() {
        return turnNumber;
    }

    public void setTurnNumber(Integer turnNumber) {
        this.turnNumber = turnNumber;
    }

    public Long getTurnId() {
        return turnId;
    }

    public void setTurnId(Long turnId) {
        this.turnId = turnId;
    }

    public Boolean getTurnComplete() {
        return turnComplete;
    }

    public void setTurnComplete(Boolean turnComplete) {
        this.turnComplete = turnComplete;
    }

    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    @Override
    public String toString() {
        return "Turn{" +
                "turnId=" + turnId +
                ", turnComplete=" + turnComplete +
                ", batch=" + batch +
                '}';
    }
}
