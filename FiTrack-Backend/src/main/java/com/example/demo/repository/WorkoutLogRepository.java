package com.example.demo.repository;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.FoodLog;
import com.example.demo.model.WorkoutLog;

public interface WorkoutLogRepository extends JpaRepository<WorkoutLog, Long> {

	Collection<WorkoutLog> findByUserId(Long id);
	Collection<WorkoutLog> findByUserIdAndDate(Long id, LocalDate date);;


}
