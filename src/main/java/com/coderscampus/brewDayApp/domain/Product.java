package com.coderscampus.brewDayApp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productId;
	private String productName;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	@OneToMany(mappedBy = "product", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	@JsonIgnoreProperties("product")
	private List<Recipe> recipes = new ArrayList<>();
	@OneToMany(mappedBy = "product", orphanRemoval = true, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	@JsonIgnoreProperties("product")
	private List<Batch> batches;
	private String selectedRecipe;



	public String getSelectedRecipe() {
		return selectedRecipe;
	}

	public void setSelectedRecipe(String selectedRecipe) {
		this.selectedRecipe = selectedRecipe;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Product() {
	}

	public List<Recipe> getRecipes() {
		return recipes;
	}

	public void setRecipes(List<Recipe> recipes) {
		this.recipes = recipes;
	}

	public List<Batch> getBatches() {
		return batches;
	}

	public void setBatches(List<Batch> batches) {
		this.batches = batches;
	}

	@Override
	public String toString() {
		return "Product{" +
				"productId=" + productId +
				", productName='" + productName + '\'' +
				", user=" + user +
				'}';
	}
}
