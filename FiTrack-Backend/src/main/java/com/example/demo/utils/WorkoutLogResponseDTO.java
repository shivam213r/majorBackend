package com.example.demo.utils;

import java.time.LocalDate;

import com.example.demo.model.WorkoutLog;

public class WorkoutLogResponseDTO {
	private Long id;
    private String workoutName;
    private Double quantity;
    private Double caloriesBurned;
    private Integer sets;
    private LocalDate date;

    public static WorkoutLogResponseDTO fromEntity(WorkoutLog log) {
        WorkoutLogResponseDTO dto = new WorkoutLogResponseDTO();
        dto.setId(log.getId());
        dto.setWorkoutName(log.getWorkout().getName());
        dto.setQuantity(log.getQuantity());
        dto.setCaloriesBurned(log.getCaloriesBurned());
        dto.setSets(log.getSets());
        dto.setDate(log.getDate());
        return dto;
    }

	public Integer getSets() {
		return sets;
	}

	public void setSets(Integer sets) {
		this.sets = sets;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWorkoutName() {
		return workoutName;
	}

	public void setWorkoutName(String workoutName) {
		this.workoutName = workoutName;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getCaloriesBurned() {
		return caloriesBurned;
	}

	public void setCaloriesBurned(Double caloriesBurned) {
		this.caloriesBurned = caloriesBurned;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
}
