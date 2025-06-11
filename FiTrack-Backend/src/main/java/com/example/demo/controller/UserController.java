package com.example.demo.controller;




import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.UserEntity;
import com.example.demo.service.UserServices;
import com.example.demo.utils.LoginRequest;



@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserServices userService;

   

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserEntity user) {
        try {
        	user.setRole("USER");
            UserEntity newUser = userService.registerUser(user);
            return ResponseEntity.ok(newUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
        	
            String token = userService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());
            

            return ResponseEntity.ok(token);
        } catch (Exception e) {
        	System.out.println("hellooo login");
            return ResponseEntity.status(405).body("Login Failed: hiiiiiiii " + e.getMessage());
        }
        
    }
    @GetMapping("/get")
    public ResponseEntity<?> getUser() {
        try {
        	 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
             UserEntity user = (UserEntity) auth.getPrincipal();
            UserEntity existUser = userService.getUserByUsername(user.getEmail());
            return ResponseEntity.ok(existUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody UserEntity profileData) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserEntity currentUser = (UserEntity) auth.getPrincipal();
            
            UserEntity updatedUser = userService.updateProfile(currentUser.getEmail(), profileData);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/password")
    public ResponseEntity<?> changePassword(@RequestBody Map<String, String> passwordData) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserEntity currentUser = (UserEntity) auth.getPrincipal();
            
            String currentPassword = passwordData.get("currentPassword");
            String newPassword = passwordData.get("newPassword");
            
            if (currentPassword == null || newPassword == null) {
                return ResponseEntity.badRequest().body("Current password and new password are required");
            }
            
            userService.changePassword(currentUser.getEmail(), currentPassword, newPassword);
            return ResponseEntity.ok("Password changed successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
