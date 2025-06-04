package com.example.demo.utils;

import com.example.demo.model.Recipe;
import com.example.demo.model.RecipeIngredient;
import com.example.demo.model.RecipeStep;
import com.example.demo.model.RecipeNutrition;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class RecipeResponseDTO {
    private Long id;
    private String name;
    private String image;
    private NutritionDTO nutrition;
    private List<String> ingredients;
    private List<String> steps;
    private String prepTime;
    private String servingSize;

    public static RecipeResponseDTO fromEntity(Recipe recipe) {
        RecipeResponseDTO dto = new RecipeResponseDTO();
        dto.setId(recipe.getId());
        dto.setName(recipe.getName());
        dto.setImage(recipe.getImageUrl());
        dto.setPrepTime(recipe.getPrepTime());
        dto.setServingSize(recipe.getServingSize());

        dto.setNutrition(NutritionDTO.fromEntity(recipe.getNutrition()));
        dto.setIngredients(recipe.getIngredients().stream()
        		 .map(RecipeIngredient::getDescription)  
        		    .collect(Collectors.toList()));

        // Convert and sort steps by stepOrder
        dto.setSteps(recipe.getSteps().stream()
        		.sorted((a, b) -> Integer.compare(a.getStepOrder(), b.getStepOrder()))
        	    .map(RecipeStep::getInstruction)         
        	    .collect(Collectors.toList()));
        return dto;
    }
}
