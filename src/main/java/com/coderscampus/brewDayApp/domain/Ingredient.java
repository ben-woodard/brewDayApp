package com.coderscampus.brewDayApp.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ingredientId;
    private String ingredientName;
    @ManyToMany(mappedBy = "ingredients", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private List<Recipe> recipes = new ArrayList<>();
    private Double amountInStock;
    private Double orderingThreshold;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Enumerated(EnumType.STRING)
    @Column(name = "ingredientType")
    private ingredientType ingredientType;
    @Enumerated(EnumType.STRING)
    @Column(name = "unitOfMeasurement")
    private unitOfMeasurement unitOfMeasurement;
    public enum ingredientType {
        MALT,
        HOP,
        ADJUNCT,
        EXTRACT,
        SALT,
        YEAST
    }

    public enum unitOfMeasurement {
        lBS,
        OZ,
        EACH
    }

    public Ingredient() {
    }

    public Long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public Double getAmountInStock() {
        return amountInStock;
    }

    public void setAmountInStock(Double amountInStock) {
        this.amountInStock = amountInStock;
    }

    public Ingredient.ingredientType getIngredientType() {
        return ingredientType;
    }

    public void setIngredientType(Ingredient.ingredientType ingredientType) {
        this.ingredientType = ingredientType;
    }

    public Ingredient.unitOfMeasurement getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public void setUnitOfMeasurement(Ingredient.unitOfMeasurement unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getOrderingThreshold() {
        return orderingThreshold;
    }

    public void setOrderingThreshold(Double warningThreshold) {
        this.orderingThreshold = warningThreshold;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "ingredientId=" + ingredientId +
                ", ingredientName='" + ingredientName + '\'' +
                ", amountInStock=" + amountInStock +
                ", ingredientType=" + ingredientType +
                ", unitOfMeasurement=" + unitOfMeasurement +
                '}';
    }
}
