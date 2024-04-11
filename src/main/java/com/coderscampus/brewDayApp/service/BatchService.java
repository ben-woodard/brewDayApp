package com.coderscampus.brewDayApp.service;

import com.coderscampus.brewDayApp.domain.*;
import com.coderscampus.brewDayApp.repository.BatchRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BatchService {

    private final UserServiceImpl userService;
    private final BatchRepository batchRepo;
    private final ProductService productService;

    public BatchService(UserServiceImpl userService, BatchRepository batchRepo, ProductService productService) {
        this.userService = userService;
        this.batchRepo = batchRepo;
        this.productService = productService;
    }

    public Batch save(Batch batch) {
        return batchRepo.save(batch);
    }

    public List<Batch> findAllByUserId(Integer userId) {
        User user = userService.findUserById(userId).orElse(null);
        return user.getProducts().stream()
                .flatMap(product -> product.getBatches().stream())
                .collect(Collectors.toList());
    }

    public Batch createNewBatch(Batch batch, BatchDTO batchDTO) {
        Product product = productService.findById(batchDTO.getProductId());
        batch.setProduct(product);
        createBatchTurns(batch);
        product.getBatches().add(batch);
        return batchRepo.save(batch);
    }

    private void createBatchTurns(Batch batch) {
        int i = 0;
        while(i < batch.getNumberOfTurns()) {
            Turn turn = new Turn();
            batch.getTurns().add(turn);
            turn.setBatch(batch);
            i++;
        }
    }
}
