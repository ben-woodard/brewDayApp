package com.coderscampus.brewDayApp.service;

import com.coderscampus.brewDayApp.domain.Batch;
import com.coderscampus.brewDayApp.domain.Product;
import com.coderscampus.brewDayApp.domain.User;
import com.coderscampus.brewDayApp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<Batch> findProductBatches(User user) {
        List<Batch> batches = new ArrayList<>();
        user.getProducts().stream()
                .map(product -> product.getBatches())
                .forEach(batches::addAll);
         return batches;
    }
}
