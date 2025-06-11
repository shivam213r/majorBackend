package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.FoodDiary;
import com.example.demo.model.Recipe;
import com.example.demo.model.WorkoutDiary;
import com.example.demo.service.AdminServices;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/admin")
public class AdminController {
	
	@Autowired
	private AdminServices adminServices;
	
	
	
	@PostMapping("/logfood")
	 public ResponseEntity<?> logFood(@RequestBody FoodDiary food) {
        try {
            FoodDiary savedLog = adminServices.logfood(food);
            return ResponseEntity.ok(savedLog);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }

    }
	
	@PostMapping("/logworkout")
	 public ResponseEntity<?> logWorkout(@RequestBody WorkoutDiary workout) {
       try {
           WorkoutDiary savedLog = adminServices.logworkout(workout);
           return ResponseEntity.ok(savedLog);
       } catch (Exception e) {
           return ResponseEntity.badRequest().body("Error: " + e.getMessage());
       }
	}
	
       @PostMapping("/logrecipe")
  	 public ResponseEntity<?> logRecipe(@RequestBody Recipe recipe) {
         try {
             Recipe savedLog = adminServices.logrecipe(recipe);
             return ResponseEntity.ok(savedLog);
         } catch (Exception e) {
             return ResponseEntity.badRequest().body("Error: " + e.getMessage());
         }

   }
       
       @GetMapping("/foods")
       public ResponseEntity<?> getAllFoods() {
           try {
               List<FoodDiary> foods = adminServices.getAllFoods();
               return ResponseEntity.ok(foods);
           } catch (Exception e) {
               return ResponseEntity.badRequest().body("Error: " + e.getMessage());
           }
       }

       @GetMapping("/food/{id}")
       public ResponseEntity<?> getFoodById(@PathVariable Long id) {
           try {
               FoodDiary food = adminServices.getFoodById(id);
               return ResponseEntity.ok(food);
           } catch (Exception e) {
               return ResponseEntity.badRequest().body("Error: " + e.getMessage());
           }
       }

       @PutMapping("/food/{id}")
       public ResponseEntity<?> updateFood(@PathVariable Long id, @RequestBody FoodDiary food) {
           try {
               FoodDiary updatedFood = adminServices.updateFood(id, food);
               return ResponseEntity.ok(updatedFood);
           } catch (Exception e) {
               return ResponseEntity.badRequest().body("Error: " + e.getMessage());
           }
       }

       @DeleteMapping("/food/{id}")
       public ResponseEntity<?> deleteFood(@PathVariable Long id) {
           try {
               adminServices.deleteFood(id);
               return ResponseEntity.ok("Food deleted successfully");
           } catch (Exception e) {
               return ResponseEntity.badRequest().body("Error: " + e.getMessage());
           }
       }

       // WORKOUT DIARY METHODS
       @GetMapping("/workouts")
       public ResponseEntity<?> getAllWorkouts() {
           try {
               List<WorkoutDiary> workouts = adminServices.getAllWorkouts();
               return ResponseEntity.ok(workouts);
           } catch (Exception e) {
               return ResponseEntity.badRequest().body("Error: " + e.getMessage());
           }
       }

       @GetMapping("/workout/{id}")
       public ResponseEntity<?> getWorkoutById(@PathVariable Long id) {
           try {
               WorkoutDiary workout = adminServices.getWorkoutById(id);
               return ResponseEntity.ok(workout);
           } catch (Exception e) {
               return ResponseEntity.badRequest().body("Error: " + e.getMessage());
           }
       }

       @PutMapping("/workout/{id}")
       public ResponseEntity<?> updateWorkout(@PathVariable Long id, @RequestBody WorkoutDiary workout) {
           try {
               WorkoutDiary updatedWorkout = adminServices.updateWorkout(id, workout);
               return ResponseEntity.ok(updatedWorkout);
           } catch (Exception e) {
               return ResponseEntity.badRequest().body("Error: " + e.getMessage());
           }
       }

       @DeleteMapping("/workout/{id}")
       public ResponseEntity<?> deleteWorkout(@PathVariable Long id) {
           try {
               adminServices.deleteWorkout(id);
               return ResponseEntity.ok("Workout deleted successfully");
           } catch (Exception e) {
               return ResponseEntity.badRequest().body("Error: " + e.getMessage());
           }
       }

       // RECIPE METHODS
       @GetMapping("/recipes")
       public ResponseEntity<?> getAllRecipes() {
           try {
               List<Recipe> recipes = adminServices.getAllRecipes();
               return ResponseEntity.ok(recipes);
           } catch (Exception e) {
               return ResponseEntity.badRequest().body("Error: " + e.getMessage());
           }
       }

       @GetMapping("/recipe/{id}")
       public ResponseEntity<?> getRecipeById(@PathVariable Long id) {
           try {
               Recipe recipe = adminServices.getRecipeById(id);
               return ResponseEntity.ok(recipe);
           } catch (Exception e) {
               return ResponseEntity.badRequest().body("Error: " + e.getMessage());
           }
       }

       @PutMapping("/recipe/{id}")
       public ResponseEntity<?> updateRecipe(@PathVariable Long id, @RequestBody Recipe recipe) {
           try {
               Recipe updatedRecipe = adminServices.updateRecipe(id, recipe);
               return ResponseEntity.ok(updatedRecipe);
           } catch (Exception e) {
               return ResponseEntity.badRequest().body("Error: " + e.getMessage());
           }
       }

       @DeleteMapping("/recipe/{id}")
       public ResponseEntity<?> deleteRecipe(@PathVariable Long id) {
           try {
               adminServices.deleteRecipe(id);
               return ResponseEntity.ok("Recipe deleted successfully");
           } catch (Exception e) {
               return ResponseEntity.badRequest().body("Error: " + e.getMessage());
           }
       }

}
