package com.example.demo.service;

import com.example.demo.model.FoodDiary;
import com.example.demo.model.FoodLog;
import com.example.demo.model.UserEntity;
import com.example.demo.model.WorkoutDiary;
import com.example.demo.model.WorkoutLog;
import com.example.demo.model.WorkoutType;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.WorkoutDiaryRepository;
import com.example.demo.repository.WorkoutLogRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

@Service
public class WorkoutLogServices {

    @Autowired
    private WorkoutLogRepository workoutLogRepository;

    @Autowired
    private WorkoutDiaryRepository workoutDiaryRepository;

    public WorkoutLog logWorkout(Long workoutId, Integer sets, Double quantity) {
       
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = (UserEntity) auth.getPrincipal();

       
        WorkoutDiary workout = workoutDiaryRepository.findById(workoutId)
                .orElseThrow(() -> new IllegalArgumentException("Workout not found"));

        
        double caloriesBurned = quantity * workout.getCaloriesPerUnit();

       
        WorkoutLog log = new WorkoutLog();
        log.setUser(user);
        log.setWorkout(workout);
        log.setQuantity(quantity);
        log.setSets(sets);
        log.setCaloriesBurned(caloriesBurned);
        log.setDate(LocalDate.now());

        return workoutLogRepository.save(log);
    }

    public Collection<WorkoutLog> getAllLogsForUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = (UserEntity) auth.getPrincipal();
        return workoutLogRepository.findByUserId(user.getId());
    }
    
    public Collection<WorkoutLog> getTodayLog() {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = (UserEntity) auth.getPrincipal();
        LocalDate date = LocalDate.now();
        return workoutLogRepository.findByUserIdAndDate(user.getId(), date);
    }
    
    public Collection<WorkoutLog> getDateLog(LocalDate date) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = (UserEntity) auth.getPrincipal();
        return workoutLogRepository.findByUserIdAndDate(user.getId(), date);
    }

    public WorkoutLog updateLog(Long logId, Long workoutId, Double newQuantity) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = (UserEntity) auth.getPrincipal();

        WorkoutLog log = workoutLogRepository.findById(logId)
                .orElseThrow(() -> new RuntimeException("Workout log not found"));

        if (!log.getUser().getId().equals(user.getId())) {
            throw new SecurityException("Unauthorized to update this log");
        }

        WorkoutDiary workout = workoutDiaryRepository.findById(workoutId)
                .orElseThrow(() -> new RuntimeException("Workout not found"));

        double newCaloriesBurned = newQuantity * workout.getCaloriesPerUnit();

        log.setWorkout(workout);
        log.setQuantity(newQuantity);
        log.setCaloriesBurned(newCaloriesBurned);

        return workoutLogRepository.save(log);
    }

	public Collection<WorkoutDiary> searchWorkout(String query) {
		  if (query == null || query.trim().isEmpty()) {
	        	return Collections.emptyList();
	        }
		return  workoutDiaryRepository.findByNameContainingIgnoreCase(query.trim());
	}
}
