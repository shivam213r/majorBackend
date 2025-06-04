package com.example.demo.repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.FoodDiary;
import com.example.demo.model.FoodLog;


public interface FoodLogRepository extends JpaRepository<FoodLog, Long> {

	 Collection<FoodLog> findByUserId(Long Id);
	 Collection<FoodLog> findByUserIdAndLoggedAt(Long id, LocalDate date);

}
