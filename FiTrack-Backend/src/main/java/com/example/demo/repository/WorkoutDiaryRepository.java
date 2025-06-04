package com.example.demo.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.WorkoutDiary;

public interface WorkoutDiaryRepository extends JpaRepository<WorkoutDiary, Long> {

	Collection<WorkoutDiary> findByNameContainingIgnoreCase(String name); 
}
