package com.example.demo.model;


import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Recipe {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String name;
	    private String imageUrl;
	    private String prepTime;
	    private String servingSize;

	    @OneToOne(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
	    private RecipeNutrition nutrition;

	    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<RecipeIngredient> ingredients = new ArrayList<>();

	    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<RecipeStep> steps = new ArrayList<>();
	
}
