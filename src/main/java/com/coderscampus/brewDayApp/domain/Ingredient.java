package com.coderscampus.brewDayApp.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ingredientId;
    private String ingredientName;
    @ManyToMany(mappedBy = "ingredients")
    private List<Recipe> recipes;
    private BigDecimal price;
    private Double amountInStock;
    @Enumerated(EnumType.STRING)
    @Column(name="ingredientType")
    private ingredientType ingredientType;
    private enum ingredientType {
        MALT,
        HOP,
        ADJUNCT,
        EXTRACT,
        SALT
    }
    @Enumerated(EnumType.STRING)
    @Column(name="unitOfMeasurement")
    private unitOfMeasurement unitOfMeasurement;
    private enum unitOfMeasurement {
        lBS,
        OZ
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    @Override
    public String toString() {
        return "Ingredient{" +
                "ingredientId=" + ingredientId +
                ", ingredientName='" + ingredientName + '\'' +
                ", recipes=" + recipes +
                ", price=" + price +
                ", amountInStock=" + amountInStock +
                ", ingredientType=" + ingredientType +
                ", unitOfMeasurement=" + unitOfMeasurement +
                '}';
    }
}
