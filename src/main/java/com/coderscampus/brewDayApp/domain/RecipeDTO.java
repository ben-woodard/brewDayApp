package com.coderscampus.brewDayApp.domain;

public class RecipeDTO {
    private Long productId;
    private Long ingredientId;
    private Double amount;
    private Long defaultRecipeId;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
    }

    public Long getDefaultRecipeId() {
        return defaultRecipeId;
    }

    public void setDefaultRecipeId(Long defaultRecipeId) {
        this.defaultRecipeId = defaultRecipeId;
    }
}
