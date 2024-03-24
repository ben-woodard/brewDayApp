package com.coderscampus.brewDayApp.repository;

import com.coderscampus.brewDayApp.domain.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Long> {

//    @Query("select b from Batch b where b.userId = :userId")
//    List<Batch> findAllByUserId(Integer userId);


}
