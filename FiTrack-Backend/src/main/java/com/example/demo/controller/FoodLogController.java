package com.example.demo.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.FoodDiary;
import com.example.demo.model.FoodLog;
import com.example.demo.service.FoodLogServices;
import com.example.demo.utils.FoodLogRequest;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/food")
public class FoodLogController {
	
	@Autowired
    private FoodLogServices foodLogService;

    @PostMapping("/log")
    public ResponseEntity<?> logFood(@RequestBody FoodLogRequest request) {
        try {
            FoodLog savedLog = foodLogService.logFood(request.getFoodId(), request.getQuantity(), request.getMeal());
            return ResponseEntity.ok(savedLog);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }

    }
    
    @GetMapping("/search")
    public Collection<FoodDiary> searchFoodByName(@RequestParam String name) {
        return foodLogService.searchFood(name);
    }

    
    @GetMapping("/fetch")
    public ResponseEntity<?> getAllLogs() {
        try {
            return ResponseEntity.ok(foodLogService.getAllLogsForUser());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    
    @GetMapping("/fetchtoday")
    public ResponseEntity<?> getTodayLog() {
        try {
            return ResponseEntity.ok(foodLogService.getTodayLog());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    
    @PutMapping("/update/{logId}")
    public ResponseEntity<?> updateFoodLog(
            @PathVariable Long logId, 
            @RequestBody FoodLogRequest request) {
        try {
            FoodLog savedLog = foodLogService.updateLog(logId,request.getFoodId(),  request.getQuantity());
            return ResponseEntity.ok(savedLog);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
//        eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJleGFtcGxlM0BleGFtcGxlLmNvbSIsImlhdCI6MTc0NjgwMjI5MiwiZXhwIjoxNzQ2ODM4MjkyfQ.6qadTyITZw-_3WVxRgZlcCBDvxIhL-ZnBkxScEywCUQ
    }
    
}
