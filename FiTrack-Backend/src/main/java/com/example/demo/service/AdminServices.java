package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.FoodDiary;
import com.example.demo.model.Recipe;
import com.example.demo.model.RecipeIngredient;
import com.example.demo.model.RecipeNutrition;
import com.example.demo.model.RecipeStep;
import com.example.demo.model.UserEntity;
import com.example.demo.model.WorkoutDiary;
import com.example.demo.repository.FoodDiaryRepository;
import com.example.demo.repository.RecipeRepository;
import com.example.demo.repository.WorkoutDiaryRepository;

@Service
public class AdminServices {
	
	@Autowired
	private FoodDiaryRepository foodRepository;
	
	@Autowired
	private WorkoutDiaryRepository workoutRepository;
		
	@Autowired
	private RecipeRepository recipeRepository;
	
	 public FoodDiary logfood(FoodDiary food) {
	        // Check if food already exists
	        if (foodRepository.existsByName(food.getName())) {
	            throw new IllegalArgumentException("food name already exist");
	        }

	        // Save food to the database
	        return foodRepository.save(food);
	    }
	 
	 public WorkoutDiary logworkout(WorkoutDiary workout) {
	        
	        if (workoutRepository.existsByName(workout.getName())) {
	            throw new IllegalArgumentException("workout name already exist");
	        }

	        
	        return workoutRepository.save(workout);
	    }
		
	 public Recipe logrecipe(Recipe recipe) {
	       
	        if (recipeRepository.existsByName(recipe.getName())) {
	            throw new IllegalArgumentException("food name already exist");
	        }
	        
	        if (recipe.getNutrition() != null) {
	            recipe.getNutrition().setRecipe(recipe);
	        }

	        if (recipe.getIngredients() != null) {
	            for (RecipeIngredient ingredient : recipe.getIngredients()) {
	                ingredient.setRecipe(recipe);
	            }
	        }

	        if (recipe.getSteps() != null) {
	            for (RecipeStep step : recipe.getSteps()) {
	                step.setRecipe(recipe);
	            }
	        }

	       
	        return recipeRepository.save(recipe);
	    }
	 
	 public List<FoodDiary> getAllFoods() {
		    return foodRepository.findAll();
		}

		public FoodDiary getFoodById(Long id) {
		    Optional<FoodDiary> food = foodRepository.findById(id);
		    if (food.isPresent()) {
		        return food.get();
		    } else {
		        throw new IllegalArgumentException("Food not found with id: " + id);
		    }
		}

		public FoodDiary updateFood(Long id, FoodDiary updatedFood) {
		    Optional<FoodDiary> existingFood = foodRepository.findById(id);
		    if (existingFood.isPresent()) {
		        FoodDiary food = existingFood.get();
		        
		        // Check if name is being changed and if new name already exists (excluding current record)
		        if (!food.getName().equals(updatedFood.getName()) && 
		            foodRepository.existsByName(updatedFood.getName())) {
		            throw new IllegalArgumentException("Food name already exists");
		        }
		        
		        // Update fields
		        food.setName(updatedFood.getName());
		        food.setCalories(updatedFood.getCalories());
		        food.setProtein(updatedFood.getProtein());
		        food.setCarbs(updatedFood.getCarbs());
		        food.setFats(updatedFood.getFats());
		        food.setSugar(updatedFood.getSugar());
		      
		        
		        return foodRepository.save(food);
		    } else {
		        throw new IllegalArgumentException("Food not found with id: " + id);
		    }
		}

		public void deleteFood(Long id) {
		    if (foodRepository.existsById(id)) {
		        foodRepository.deleteById(id);
		    } else {
		        throw new IllegalArgumentException("Food not found with id: " + id);
		    }
		}

		// WORKOUT DIARY METHODS
		public List<WorkoutDiary> getAllWorkouts() {
		    return workoutRepository.findAll();
		}

		public WorkoutDiary getWorkoutById(Long id) {
		    Optional<WorkoutDiary> workout = workoutRepository.findById(id);
		    if (workout.isPresent()) {
		        return workout.get();
		    } else {
		        throw new IllegalArgumentException("Workout not found with id: " + id);
		    }
		}

		public WorkoutDiary updateWorkout(Long id, WorkoutDiary updatedWorkout) {
		    Optional<WorkoutDiary> existingWorkout = workoutRepository.findById(id);
		    if (existingWorkout.isPresent()) {
		        WorkoutDiary workout = existingWorkout.get();
		        
		        // Check if name is being changed and if new name already exists (excluding current record)
		        if (!workout.getName().equals(updatedWorkout.getName()) && 
		            workoutRepository.existsByName(updatedWorkout.getName())) {
		            throw new IllegalArgumentException("Workout name already exists");
		        }
		        
		        // Update fields
		        workout.setName(updatedWorkout.getName());
		        workout.setCategory(updatedWorkout.getCategory());
		        workout.setDifficulty(updatedWorkout.getDifficulty());
		        workout.setCaloriesPerUnit(updatedWorkout.getCaloriesPerUnit());
		        workout.setType(updatedWorkout.getType());
		        workout.setType(updatedWorkout.getType());
		        // Add other fields as needed based on your WorkoutDiary model
		        
		        return workoutRepository.save(workout);
		    } else {
		        throw new IllegalArgumentException("Workout not found with id: " + id);
		    }
		}

		public void deleteWorkout(Long id) {
		    if (workoutRepository.existsById(id)) {
		        workoutRepository.deleteById(id);
		    } else {
		        throw new IllegalArgumentException("Workout not found with id: " + id);
		    }
		}

		
		public List<Recipe> getAllRecipes() {
		    return recipeRepository.findAll();
		}

		public Recipe getRecipeById(Long id) {
		    Optional<Recipe> recipe = recipeRepository.findById(id);
		    if (recipe.isPresent()) {
		        return recipe.get();
		    } else {
		        throw new IllegalArgumentException("Recipe not found with id: " + id);
		    }
		}

		public Recipe updateRecipe(Long id, Recipe updatedRecipe) {
		    Optional<Recipe> existingRecipe = recipeRepository.findById(id);
		    if (existingRecipe.isPresent()) {
		        Recipe recipe = existingRecipe.get();

		        // Check if name is being changed and if new name already exists (excluding current record)
		        if (!recipe.getName().equals(updatedRecipe.getName()) &&
		            recipeRepository.existsByName(updatedRecipe.getName())) {
		            throw new IllegalArgumentException("Recipe name already exists");
		        }

		        // Update basic fields
		        recipe.setName(updatedRecipe.getName());
		        recipe.setImageUrl(updatedRecipe.getImageUrl());
		        recipe.setPrepTime(updatedRecipe.getPrepTime());
		        recipe.setServingSize(updatedRecipe.getServingSize());

		       
		        if (updatedRecipe.getNutrition() != null) {
		            RecipeNutrition newNutri = updatedRecipe.getNutrition();

		            if (recipe.getNutrition() == null) {
		                newNutri.setRecipe(recipe);
		                recipe.setNutrition(newNutri);
		            } else {
		                RecipeNutrition existingNutri = recipe.getNutrition();
		                existingNutri.setCalories(newNutri.getCalories());
		                existingNutri.setProtein(newNutri.getProtein());
		                existingNutri.setCarbs(newNutri.getCarbs());
		                existingNutri.setFat(newNutri.getFat());
		                existingNutri.setSugar(newNutri.getSugar());
		            }
		        }

		        // Update ingredients
		        if (updatedRecipe.getIngredients() != null) {
		            recipe.getIngredients().clear();
		            for (RecipeIngredient ingredient : updatedRecipe.getIngredients()) {
		                ingredient.setId(null); 
		                ingredient.setRecipe(recipe);
		                recipe.getIngredients().add(ingredient);
		            }
		        }

		        // Update steps
		        if (updatedRecipe.getSteps() != null) {
		            recipe.getSteps().clear();
		            for (RecipeStep step : updatedRecipe.getSteps()) {
		                step.setId(null); 
		                step.setRecipe(recipe);
		                recipe.getSteps().add(step);
		            }
		        }

		        return recipeRepository.save(recipe);
		    } else {
		        throw new IllegalArgumentException("Recipe not found with id: " + id);
		    }
		}

		public void deleteRecipe(Long id) {
		    if (recipeRepository.existsById(id)) {
		        recipeRepository.deleteById(id);
		    } else {
		        throw new IllegalArgumentException("Recipe not found with id: " + id);
		    }
		}
		
}
