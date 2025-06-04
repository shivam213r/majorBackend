package com.example.demo.service;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.model.Recipe;
import com.example.demo.repository.RecipeRepository;

@Service
public class RecipeServices {

    @Autowired
    private RecipeRepository recipeRepository;

    public Page<Recipe> getRecipes(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        if (name == null || name.isBlank()) {
            return recipeRepository.findAll(pageable);
        } else {
            return recipeRepository.findByNameContainingIgnoreCase(name, pageable);
        }
    }

    public Recipe getRecipeById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Recipe not found with ID: " + id));
    }
}

