package com.example.demo.model;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "food_diary")
public class FoodDiary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String name;

    @Column(name = "portion_description", nullable = false)
    private String portionDescription;
    
    @Column(unique = true, nullable = false)
    private float calories;
    private float protein;
    private float carbs;
    private float fats;
    private float sugar;
}