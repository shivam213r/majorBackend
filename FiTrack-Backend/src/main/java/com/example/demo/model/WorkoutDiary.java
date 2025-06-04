package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "workout_diary")
@Data
public class WorkoutDiary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String category;

    @Enumerated(EnumType.STRING)
    private WorkoutType type; // REPS or DURATION

    @Column(name = "calories_per_unit")
    private Double caloriesPerUnit;

    @Enumerated(EnumType.STRING)
    private DificultyLevel difficulty;

}

