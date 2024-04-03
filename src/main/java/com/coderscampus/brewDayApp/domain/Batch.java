package com.coderscampus.brewDayApp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Batch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long batchId;
    private Integer batchNumber;
    private Integer numberOfTurns;
    private LocalDate startDate;
    private LocalDate endDate;
    private String tankName;
    private Boolean batchComplete;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    @OneToMany(mappedBy = "batch", orphanRemoval = true, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonIgnoreProperties("batch")
    private List<Turn> turns = new ArrayList<>();

    public Batch() {
    }

    public long getBatchId() {
        return batchId;
    }

    public void setBatchId(long batchId) {
        this.batchId = batchId;
    }

    public Integer getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(Integer batchNumber) {
        this.batchNumber = batchNumber;
    }

    public Integer getNumberOfTurns() {
        return numberOfTurns;
    }

    public void setNumberOfTurns(Integer numberOfTurns) {
        this.numberOfTurns = numberOfTurns;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getTankName() {
        return tankName;
    }

    public void setTankName(String tankName) {
        this.tankName = tankName;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Turn> getTurns() {
        return turns;
    }

    public void setTurns(List<Turn> turns) {
        this.turns = turns;
    }

    public Boolean getBatchComplete() {
        return batchComplete;
    }

    public void setBatchComplete(Boolean batchComlete) {
        this.batchComplete = batchComlete;
    }

    @Override
    public String toString() {
        return "Batch{" +
                "batchId=" + batchId +
                ", batchNumber=" + batchNumber +
                ", numberOfTurns=" + numberOfTurns +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", tankName='" + tankName + '\'' +
                ", product=" + product +
                '}';
    }
}
