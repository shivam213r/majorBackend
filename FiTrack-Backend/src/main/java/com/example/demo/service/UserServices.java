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
    
 

    public UserEntity updateProfile(String email, UserEntity profileData) {
        UserEntity existingUser = getUserByEmail(email);
        
        // Check username uniqueness (if username is being changed)
        if (profileData.getUsername() != null && 
            !profileData.getUsername().equals(existingUser.getUsername())) {
            if (userRepository.existsByUsernameAndIdNot(profileData.getUsername(), existingUser.getId())) {
                throw new RuntimeException("Username already exists");
            }
            existingUser.setUsername(profileData.getUsername());
        }
        
        // Update allowed fields only
        if (profileData.getFname() != null) {
            existingUser.setFname(profileData.getFname());
        }
        if (profileData.getLname() != null) {
            existingUser.setLname(profileData.getLname());
        }
        if (profileData.getWeight() != null) {
            existingUser.setWeight(profileData.getWeight());
        }
        if (profileData.getHeight() != null) {
            existingUser.setHeight(profileData.getHeight());
        }
        if (profileData.getActivity() != null) {
            // Validate activity level
            if (isValidActivityLevel(profileData.getActivity())) {
                existingUser.setActivity(profileData.getActivity());
            } else {
                throw new RuntimeException("Invalid activity level");
            }
        }
        
        return userRepository.save(existingUser);
    }

    public void changePassword(String email, String currentPassword, String newPassword) {
        UserEntity user = getUserByEmail(email);
        
        // Verify current password
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }
        
        // Validate new password (add your own rules)
        if (newPassword == null || newPassword.length() < 6) {
            throw new RuntimeException("New password must be at least 6 characters");
        }
        
        // Update password
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    private boolean isValidActivityLevel(String activity) {
        return activity.equals("NOT_VERY_ACTIVE") || 
               activity.equals("LIGHTLY_ACTIVE") || 
               activity.equals("MODERATELY_ACTIVE") || 
               activity.equals("HIGHLY_ACTIVE");
    }

    // Helper method if you don't have it
    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }

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
        return jwtUtils.generateToken(email, user.getRole());
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
