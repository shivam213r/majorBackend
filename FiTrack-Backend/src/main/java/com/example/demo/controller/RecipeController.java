package com.example.demo.controller;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Recipe;
import com.example.demo.service.RecipeServices;
import com.example.demo.utils.RecipeResponseDTO;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    @Autowired
    private RecipeServices recipeService;

    @GetMapping
    public ResponseEntity<?> getRecipes(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size) {
        try {
            Page<Recipe> recipePage = recipeService.getRecipes(name, page, size);
            List<RecipeResponseDTO> recipes = recipePage.getContent().stream()
                    .map(RecipeResponseDTO::fromEntity)
                    .collect(Collectors.toList());
            
            return ResponseEntity.ok()
                    .header("X-Total-Pages", String.valueOf(recipePage.getTotalPages()))
                    .header("X-Current-Page", String.valueOf(recipePage.getNumber()))
                    .body(recipes);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRecipeById(@PathVariable Long id) {
        try {
            Recipe recipe = recipeService.getRecipeById(id);
            return ResponseEntity.ok(RecipeResponseDTO.fromEntity(recipe));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
