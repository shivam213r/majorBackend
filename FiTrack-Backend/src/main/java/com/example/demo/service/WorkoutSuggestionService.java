package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.utils.WorkoutSuggestionResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class WorkoutSuggestionService {
    
    @Autowired
    private DailyWorkoutSuggestionRepository suggestionRepository;
    
    @Autowired
    private WorkoutDiaryRepository workoutRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private GeminiApiService geminiApiService;
    
    public WorkoutSuggestionResponseDto getDailyWorkoutSuggestion() {
        LocalDate today = LocalDate.now();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = (UserEntity) auth.getPrincipal();
        
        // Check if suggestions already exist for today
        Optional<DailyWorkoutSuggestion> existingSuggestion = 
            suggestionRepository.findByUserIdAndSuggestionDate(user.getId(), today);
            
        if (existingSuggestion.isPresent()) {
            return WorkoutSuggestionResponseDto.builder()
                .suggestions(existingSuggestion.get().getSuggestedWorkouts())
                .date(today.toString())
                
                .build();
        }
        
        // Generate new suggestions
        DificultyLevel userDifficulty = mapActivityToDifficulty(user.getActivity());
        
        
        // Fetch workouts based on difficulty
        List<WorkoutDiary> availableWorkouts = workoutRepository.findByDifficulty(userDifficulty);
       
        
        // Call Gemini API
        String geminiSuggestions = geminiApiService.generateWorkoutSuggestions(user, availableWorkouts);
        
        // Cache the suggestions
        DailyWorkoutSuggestion newSuggestion = DailyWorkoutSuggestion.builder()
            .user(user)
            .suggestionDate(today)
            .suggestedWorkouts(geminiSuggestions)
            .build();
            
        suggestionRepository.save(newSuggestion);
        
        return WorkoutSuggestionResponseDto.builder()
            .suggestions(geminiSuggestions)
            .date(today.toString())
            .build();
    }
    
    private DificultyLevel mapActivityToDifficulty(String activity) {
        if (activity == null) return DificultyLevel.BEGINNER;
        
        String activityLevel = activity.toLowerCase().split(" ")[0];
        
        switch (activityLevel) {
            case "not":
            	
            	 return DificultyLevel.BEGINNER;
            case "lightly":
            	
                return DificultyLevel.BEGINNER;
            case "moderately":
            	
                return DificultyLevel.INTERMEDIATE;
            case "highly":
            	
                return DificultyLevel.ADVANCED;
            default:
                return DificultyLevel.BEGINNER;
        }
    }
    public void updateDailyWorkoutSuggestion(String updatedSuggestions) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = (UserEntity) auth.getPrincipal();
        LocalDate today = LocalDate.now();
        
        DailyWorkoutSuggestion suggestion = suggestionRepository.findByUserIdAndSuggestionDate(user.getId(), today)
            .orElseThrow(() -> new RuntimeException("No suggestion found for today"));
        
        suggestion.setSuggestedWorkouts(updatedSuggestions);
        suggestionRepository.save(suggestion);
    }
}