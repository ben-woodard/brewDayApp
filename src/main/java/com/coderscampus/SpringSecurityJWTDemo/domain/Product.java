package com.coderscampus.SpringSecurityJWTDemo.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
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
//	@OneToMany(mappedBy = "brand", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
//	private List<Recipe> recipes;
//	@OneToMany(mappedBy = "brand", orphanRemoval = true, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
//	private List<Batch> batches;


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
}
