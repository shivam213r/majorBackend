package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class RecipeNutrition {
    @Id
    private Long recipeId;

    @OneToOne
    @JsonIgnore
    @MapsId
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    private Double calories;
    private Double protein;
    private Double fat;
    private Double carbs;
    private Double fiber;
    private Double sodium;
    private Double sugar;
}

