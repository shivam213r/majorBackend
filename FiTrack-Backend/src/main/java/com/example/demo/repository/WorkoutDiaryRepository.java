package com.example.demo.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.*;

public interface WorkoutDiaryRepository extends JpaRepository<WorkoutDiary, Long> {

	Collection<WorkoutDiary> findByNameContainingIgnoreCase(String name); 
	List<WorkoutDiary> findByDifficulty(DificultyLevel userDifficulty);
	boolean existsByName(String name);
}
