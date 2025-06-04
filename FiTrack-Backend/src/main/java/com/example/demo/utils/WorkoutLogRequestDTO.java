package com.example.demo.utils;

public class WorkoutLogRequestDTO {
	 private Long workoutId;
	 private Integer sets;
	    public Integer getSets() {
		return sets;
	}
	public void setSets(Integer sets) {
		this.sets = sets;
	}
		private Double quantity;
		public Long getWorkoutId() {
			return workoutId;
		}
		public void setWorkoutId(Long workoutId) {
			this.workoutId = workoutId;
		}
		public Double getQuantity() {
			return quantity;
		}
		public void setQuantity(Double quantity) {
			this.quantity = quantity;
		}
	    
	    

}
