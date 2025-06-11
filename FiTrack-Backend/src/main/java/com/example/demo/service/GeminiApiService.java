package com.example.demo.service;

import com.example.demo.model.UserEntity;
import com.example.demo.model.WorkoutDiary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.*;

@Service
public class GeminiApiService {
    
    @Value("${gemini.api.key}")
    private String apiKey;
    
    @Value("${gemini.api.url:https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent}")
    private String apiUrl;
    
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    public String generateWorkoutSuggestions(UserEntity user, List<WorkoutDiary> availableWorkouts) {
        try {
            String prompt = buildPrompt(user, availableWorkouts);
            
            Map<String, Object> requestBody = new HashMap<>();
            Map<String, Object> content = new HashMap<>();
            Map<String, String> part = new HashMap<>();
            
            part.put("text", prompt);
            content.put("parts", Arrays.asList(part));
            requestBody.put("contents", Arrays.asList(content));
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
            
            String url = apiUrl + "?key=" + apiKey;
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
            
            return extractTextFromResponse(response.getBody());
            
        } catch (Exception e) {
            e.printStackTrace();
            return "Error generating workout suggestions. Please try again later.";
        }
    }
    
    private String buildPrompt(UserEntity user, List<WorkoutDiary> workouts) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("You are a fitness trainer creating a daily workout routine. ");
        prompt.append("User profile:\n");
        prompt.append("- Activity Level: ").append(user.getActivity()).append("\n");
        prompt.append("- Goal: ").append(user.getGoal()).append("\n");
        prompt.append("- Gender: ").append(user.getGender()).append("\n");
        
        if (user.getAdgoal1() != null) {
            prompt.append("- Additional Goal 1: ").append(user.getAdgoal1()).append("\n");
        }
        if (user.getAdgoal2() != null) {
            prompt.append("- Additional Goal 2: ").append(user.getAdgoal2()).append("\n");
        }
        
        prompt.append("\nAvailable workouts (JSON format):\n");
        prompt.append("[\n");
        
        for (int i = 0; i < workouts.size(); i++) {
            WorkoutDiary workout = workouts.get(i);
            prompt.append("  {\n");
            prompt.append("    \"id\": ").append(workout.getId()).append(",\n");
            prompt.append("    \"name\": \"").append(workout.getName()).append("\",\n");
            prompt.append("    \"category\": \"").append(workout.getCategory()).append("\",\n");
            prompt.append("    \"type\": \"").append(workout.getType()).append("\",\n");
            prompt.append("    \"difficulty\": \"").append(workout.getDifficulty()).append("\",\n");
            prompt.append("    \"caloriesPerUnit\": ").append(workout.getCaloriesPerUnit()).append("\n");
            prompt.append("  }");
            if (i < workouts.size() - 1) {
                prompt.append(",");
            }
            prompt.append("\n");
        }
        
        prompt.append("]\n\n");
        
        prompt.append("IMPORTANT: Return ONLY a valid JSON array with selected workouts for today's routine. ");
        prompt.append("For each selected workout, copy the exact workout object and add these fields:\n");
        prompt.append("- \"sets\": number (for strength exercises, 1 for cardio)\n");
        prompt.append("- \"recommendedQuantity\": number (reps for REPS type, minutes for DURATION type)\n");
        prompt.append("- \"instructions\": string (brief exercise instructions)\n\n");
        
        prompt.append("Select 8 workouts for a balanced routine. Return only the JSON array, no other text.");
        
        return prompt.toString();
    }
    
    private String extractTextFromResponse(String jsonResponse) {
        try {
            Map<String, Object> response = objectMapper.readValue(jsonResponse, Map.class);
            List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.get("candidates");
            
            if (candidates != null && !candidates.isEmpty()) {
                Map<String, Object> candidate = candidates.get(0);
                Map<String, Object> content = (Map<String, Object>) candidate.get("content");
                List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
                
                if (parts != null && !parts.isEmpty()) {
                    String rawText = (String) parts.get(0).get("text");
                    
                    // Clean up the response to extract just the JSON array
                    rawText = rawText.trim();
                    
                    // Find JSON array bounds
                    int startIndex = rawText.indexOf('[');
                    int endIndex = rawText.lastIndexOf(']');
                    
                    if (startIndex != -1 && endIndex != -1 && endIndex > startIndex) {
                        return rawText.substring(startIndex, endIndex + 1);
                    }
                    
                    return rawText;
                }
            }
            
            return "[]";
        } catch (Exception e) {
            e.printStackTrace();
            return "[]";
        }
    }
}
