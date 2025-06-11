package com.example.demo.utils;
import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class WorkoutSuggestionResponseDto {
	 private String suggestions;
	    private String date;
}
