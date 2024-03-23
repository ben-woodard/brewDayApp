package com.coderscampus.brewDayApp.service;

import com.coderscampus.brewDayApp.domain.Product;
import com.coderscampus.brewDayApp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepo;

    @Autowired
    public ProductService(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    public Product save(Product product) {
        return productRepo.save(product);
    }
}
