package com.dhakatraffic.tms.controller;

import com.dhakatraffic.tms.model.User;
import com.dhakatraffic.tms.repository.UserRepository;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    
    // 1. We create the BCrypt cryptographic machine here
    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder = 
        new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody User newUser) {
        // Check if the username already exists in your XAMPP database
        Optional<User> existingUser = userRepository.findByUsername(newUser.getUsername());
        if (existingUser.isPresent()) {
            return "❌ Error: Username is already taken!";
        }
        
        // 2. This is the magic line! It scrambles the password before saving
        String scrambledPassword = passwordEncoder.encode(newUser.getPasswordHash());
        newUser.setPasswordHash(scrambledPassword);
        
        // 3. Save the user with the scrambled password into MySQL
        userRepository.save(newUser);
        return "✅ User '" + newUser.getUsername() + "' registered successfully into MySQL Database!";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password) {
        return userRepository.findByUsername(username)
                .map(user -> {
                    if (passwordEncoder.matches(password, user.getPasswordHash())) {
                        return "🔓 Login Successful!";
                    }
                    return "❌ Error: Invalid password.";
                }).orElse("❌ Error: User not found.");
    }
}