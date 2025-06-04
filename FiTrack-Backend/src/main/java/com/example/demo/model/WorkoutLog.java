package com.example.demo.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "workout_log")
@Data
public class WorkoutLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "workout_id")
    private WorkoutDiary workout;
    
    
    private Integer sets;

    private Double quantity; // reps, minutes, etc.

    @Column(name = "calories_burned")
    private Double caloriesBurned;

    private LocalDate date;

    // Getters & Setters
}
