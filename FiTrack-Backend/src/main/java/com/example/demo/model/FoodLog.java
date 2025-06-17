package com.example.demo.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "food_log")
public class FoodLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "food_id", nullable = false)
    private FoodDiary food;
    
    @Enumerated(EnumType.STRING)
    private Meal meal;

    private String quantity;

    @Column(name = "logged_at")
    private LocalDate loggedAt = LocalDate.now();

   
}

