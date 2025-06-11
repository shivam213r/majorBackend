package com.example.demo.service;

import com.example.demo.model.FoodDiary;
import com.example.demo.model.FoodLog;
import com.example.demo.model.Meal;
import com.example.demo.model.UserEntity;
import com.example.demo.model.WorkoutLog;
import com.example.demo.repository.FoodDiaryRepository;
import com.example.demo.repository.FoodLogRepository;
import com.example.demo.repository.UserRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
public class FoodLogServices {

    @Autowired
    private FoodLogRepository foodLogRepository;

    @Autowired
    private FoodDiaryRepository foodDiaryRepository;

    

    public FoodLog logFood(Long foodId, String quantity, Meal meal) {
        // Get authenticated user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = (UserEntity) auth.getPrincipal();

        // Fetch food item
        FoodDiary food = foodDiaryRepository.findById(foodId)
                .orElseThrow(() -> new IllegalArgumentException("Food not found"));

        // Create and save log
        FoodLog log = new FoodLog();
        log.setUser(user);
        log.setFood(food);
        log.setMeal(meal);
        log.setQuantity(quantity);
        log.setLoggedAt(LocalDate.now());

        return foodLogRepository.save(log);
    }
    
    public Collection<FoodLog> getAllLogsForUser() {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = (UserEntity) auth.getPrincipal();
        Long userId = user.getId();
        return foodLogRepository.findByUserId(userId);
    }
    public Collection<FoodLog> getTodayLog() {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = (UserEntity) auth.getPrincipal();
        LocalDate date = LocalDate.now();
        return foodLogRepository.findByUserIdAndLoggedAt(user.getId(), date);
    }
    
    public Collection<FoodLog> getDateLog(LocalDate date) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = (UserEntity) auth.getPrincipal();
        return foodLogRepository.findByUserIdAndLoggedAt(user.getId(), date);
    }
    
    public Collection<FoodDiary> searchFood(String query) {
        if (query == null || query.trim().isEmpty()) {
        	return Collections.emptyList();
        }
        return foodDiaryRepository.findByNameContainingIgnoreCase(query.trim());
    }
    
    
    public FoodLog updateLog(Long logId, Long foodId, String newQuantity) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = (UserEntity) auth.getPrincipal();
        Long userId = user.getId();

        FoodLog log = foodLogRepository.findById(logId)
            .orElseThrow(() -> new RuntimeException("Log not found"));

        if (!log.getUser().getId().equals(userId)) {
            throw new SecurityException("Unauthorized to update this log");
        }

  
        FoodDiary foodDiary = foodDiaryRepository.findById(foodId)
            .orElseThrow(() -> new RuntimeException("Food item not found"));

     
        log.setFood(foodDiary); 
        log.setQuantity(newQuantity);

        return foodLogRepository.save(log);
    }
    
    public void deleteFoodLog(Long logId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = (UserEntity) auth.getPrincipal();

        FoodLog log = foodLogRepository.findById(logId)
            .orElseThrow(() -> new IllegalArgumentException("Log not found"));

        if (!log.getUser().getId().equals(user.getId())) {
            throw new SecurityException("You are not allowed to delete this log");
        }

        foodLogRepository.delete(log);
    }

	
}

