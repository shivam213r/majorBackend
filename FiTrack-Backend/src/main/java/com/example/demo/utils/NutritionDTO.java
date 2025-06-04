package com.example.demo.utils;



import com.example.demo.model.Recipe;
import com.example.demo.model.RecipeIngredient;
import com.example.demo.model.RecipeNutrition;

import lombok.Data;

@Data
public class NutritionDTO {
	 private Double calories;
	    private Double protein;
	    private Double fat;
	    private Double carbs;
	    private Double fiber;
	    private Double sodium;
	    private Double sugar;

    public static NutritionDTO fromEntity(RecipeNutrition recipe) {
        NutritionDTO dto = new NutritionDTO();
        dto.setCalories(recipe.getCalories());
        dto.setProtein(recipe.getProtein());
        dto.setFat(recipe.getFat());
        dto.setCarbs(recipe.getCarbs());
        dto.setFiber(recipe.getFiber());
        dto.setSodium(recipe.getSodium());
        dto.setSugar(recipe.getSugar());
        return dto;
    }
}
