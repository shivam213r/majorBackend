package com.example.demo.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;
    
    @Column(unique = true, nullable = false)
   
    private String username;
    private String fname;
    private String lname;

    private Double height; // cm
    private Double weight; // kg
    private String gender;
    private String goal; // e.g. lose, maintain, gain
    private String activity;
    private String adgoal1;
    private String adgoal2;
    private LocalDate dob;
    private String role;
   

    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

	

}
