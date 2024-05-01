package com.coderscampus.brewDayApp.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "company_orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private String companyName;
    private Boolean orderReceived;
    private LocalDate orderReceivedDate;
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @JoinTable(name = "order_ingredient",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private List<Ingredient> ingredients = new ArrayList<>();
    @ElementCollection
    @CollectionTable(name = "order_ingredient_to_add", joinColumns = @JoinColumn(name = "order_id"))
    @MapKeyColumn(name = "ingredient_id")
    @Column(name = "amount_to_add")
    private Map<Long, Double> ingredientsToAdd = new HashMap<>();

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Boolean getOrderReceived() {
        return orderReceived;
    }

    public void setOrderReceived(Boolean orderReceived) {
        this.orderReceived = orderReceived;
    }

    public LocalDate getOrderReceivedDate() {
        return orderReceivedDate;
    }

    public void setOrderReceivedDate(LocalDate orderReceivedDate) {
        this.orderReceivedDate = orderReceivedDate;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Map<Long, Double> getIngredientsToAdd() {
        return ingredientsToAdd;
    }

    public void setIngredientsToAdd(Map<Long, Double> ingredientsToAdd) {
        this.ingredientsToAdd = ingredientsToAdd;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", companyName='" + companyName + '\'' +
                ", ingredientsToAdd=" + ingredientsToAdd +
                '}';
    }
}
