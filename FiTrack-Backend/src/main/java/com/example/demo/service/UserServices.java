package com.example.demo.service;
import com.example.demo.model.UserEntity;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

  
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Register a new user
    public UserEntity registerUser(UserEntity user) {
        // Check if user already exists
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username already taken");
        }

        // Hash password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // Save user to the database
        return userRepository.save(user);
    }

    
    // Login user and generate JWT token
    public String loginUser(String email, String password) {
        Optional<UserEntity> userOptional = userRepository.findByEmail(email);

        if (!userOptional.isPresent()) {
            throw new IllegalArgumentException("User not found");
        }

        UserEntity user = userOptional.get();

        // Check if passwords match
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Incorrect password");
        }

        // Generate JWT token
        return jwtUtils.generateToken(email);
    }

    // Get user details by username
    public UserEntity getUserByUsername(String email) {
        Optional<UserEntity> userOptional = userRepository.findByEmail(email);
        if (!userOptional.isPresent()) {
            throw new IllegalArgumentException("User not found");
        }
        return userOptional.get();
    }

    // Get user details by user ID
    public UserEntity getUserById(Long id) {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            throw new IllegalArgumentException("User not found");
        }
        return userOptional.get();
    }
}
