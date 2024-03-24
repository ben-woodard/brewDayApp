package com.coderscampus.brewDayApp.service;

import com.coderscampus.brewDayApp.domain.Batch;
import com.coderscampus.brewDayApp.repository.BatchRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BatchService {

  private final BatchRepository batchRepo;

    public BatchService(BatchRepository batchRepo) {
        this.batchRepo = batchRepo;
    }

//    public List<Batch> findAllByUserId(Integer userId) {
//        return batchRepo.findAllByUserId(userId);
//    }
}
