package com.example.demo.controller;
import com.example.demo.utils.WorkoutLogRequestDTO;
import com.example.demo.utils.WorkoutLogResponseDTO;
import com.example.demo.model.FoodDiary;
import com.example.demo.model.WorkoutDiary;
import com.example.demo.model.WorkoutLog;
import com.example.demo.service.WorkoutLogServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/workouts/log")
public class WorkoutLogController {

    @Autowired
    private WorkoutLogServices workoutLogServices;

    @PostMapping
    public ResponseEntity<?> logWorkout(@RequestBody WorkoutLogRequestDTO request) {
        WorkoutLog log = workoutLogServices.logWorkout(request.getWorkoutId(), request.getSets(), request.getQuantity());
        return ResponseEntity.ok(log);
    }
    
    @GetMapping("/search")
    public Collection<WorkoutDiary> searchWorkoutByName(@RequestParam String name) {
        return workoutLogServices.searchWorkout(name);
    }
    
    @GetMapping("/fetchtoday")
    public ResponseEntity<?> getTodayLog() {
        try {
            return ResponseEntity.ok(workoutLogServices.getTodayLog());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    
    @GetMapping("/fetchDate")
    public ResponseEntity<?> getDateLog(@RequestParam LocalDate date) {
        try {
            return ResponseEntity.ok(workoutLogServices.getDateLog(date));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    
    @GetMapping
    public Collection<WorkoutLogResponseDTO> getAllLogsForUser() {
        return workoutLogServices.getAllLogsForUser().stream()
                .map(WorkoutLogResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @PutMapping("update/{logId}")
    public WorkoutLogResponseDTO updateWorkoutLog(@PathVariable Long logId, @RequestBody WorkoutLogRequestDTO request) {
        WorkoutLog log = workoutLogServices.updateLog(logId, request.getWorkoutId(), request.getSets(), request.getQuantity());
        return WorkoutLogResponseDTO.fromEntity(log);
    }
    
    @DeleteMapping("delete/{logId}")
    public ResponseEntity<?> deleteWorkoutLog(@PathVariable Long logId) {
        workoutLogServices.deleteLog(logId);
        return ResponseEntity.ok().build();
    }

}
