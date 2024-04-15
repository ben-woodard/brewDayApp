package com.coderscampus.brewDayApp.service;

import com.coderscampus.brewDayApp.domain.*;
import com.coderscampus.brewDayApp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepo;
    private final UserServiceImpl userService;

    @Autowired
    public ProductService(ProductRepository productRepo, UserServiceImpl userService) {
        this.productRepo = productRepo;
        this.userService = userService;
    }

    public Product save(Product product) {
        return productRepo.save(product);
    }

    public Product findById(Long productId) {
        return productRepo.findById(productId).orElse(null);
    }

    public List<Batch> findProductBatches(User user) {
        List<Batch> batches = new ArrayList<>();
        user.getProducts().stream()
                .map(product -> product.getBatches())
                .forEach(batches::addAll);
         return batches;
    }

    public Product createProductUserRelationship(Product product, Integer userId) {
        User user = userService.findUserById(userId).orElse(null);
        product.setUser(user);
        user.getProducts().add(product);
        return productRepo.save(product);
    }

    public void delete(Product product) {
        User user =  product.getUser();
        user.getProducts().remove(product);
        product.getRecipes().removeAll(product.getRecipes());
        productRepo.delete(product);
    }

    public void setDefaultRecipe(Product savedProduct, RecipeDTO recipeDTO) {
        savedProduct.setDefaultRecipeId(recipeDTO.getDefaultRecipeId());
        productRepo.save(savedProduct);
    }
}
