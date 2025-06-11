package com.example.demo.controller;

import com.example.demo.service.WorkoutSuggestionService;
import com.example.demo.utils.WorkoutSuggestionResponseDto;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/suggestions")
@CrossOrigin(origins = "*")
public class WorkoutSuggestionController {
    
    @Autowired
    private WorkoutSuggestionService suggestionService;
    
    @GetMapping("/daily-workout")
    public ResponseEntity<WorkoutSuggestionResponseDto> getDailyWorkoutSuggestion() {
        try {
            
            
            WorkoutSuggestionResponseDto response = suggestionService.getDailyWorkoutSuggestion();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                WorkoutSuggestionResponseDto.builder()
                    .suggestions("Error generating suggestions: " + e.getMessage())
                    
                    .build()
            );
        }
    }
    
    @PutMapping("/update-daily-workout")
    public ResponseEntity<String> updateDailyWorkoutSuggestion(@RequestBody Map<String, String> request) {
        try {
            String updatedSuggestions = request.get("suggestions");
            suggestionService.updateDailyWorkoutSuggestion(updatedSuggestions);
            return ResponseEntity.ok("Updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating suggestions: " + e.getMessage());
        }
    }
}

