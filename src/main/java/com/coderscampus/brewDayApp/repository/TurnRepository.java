package com.coderscampus.brewDayApp.repository;

import com.coderscampus.brewDayApp.domain.Turn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TurnRepository extends JpaRepository<Turn, Long> {
}
