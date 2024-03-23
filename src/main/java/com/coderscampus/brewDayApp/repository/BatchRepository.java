package com.coderscampus.brewDayApp.repository;

import com.coderscampus.brewDayApp.domain.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Long> {
}
