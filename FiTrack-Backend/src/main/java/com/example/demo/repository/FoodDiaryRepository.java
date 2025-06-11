package com.example.demo.repository;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.*;
public interface FoodDiaryRepository extends JpaRepository<FoodDiary,Long> {

	Collection<FoodDiary> findByNameContainingIgnoreCase(String name);
	 boolean existsByName(String name);
	

}
