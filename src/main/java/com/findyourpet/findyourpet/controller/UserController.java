package com.findyourpet.findyourpet.controller;

import com.findyourpet.findyourpet.model.User;
import com.findyourpet.findyourpet.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return service.register(user);
    }

    @PostMapping("/login")
    public User login(@RequestBody User credentials) {
        Optional<User> user = service.login(credentials.getEmail(), credentials.getPassword());
        return user.orElseThrow(() -> new RuntimeException("Invalid credentials"));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        service.processForgotPassword(email);
        return ResponseEntity.ok(Map.of("message", "If email exists, link sent."));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> payload) {
        String token = payload.get("token");
        String password = payload.get("password");

        boolean success = service.processResetPassword(token, password);

        if (success) {
            return ResponseEntity.ok(Map.of("message", "Password changed successfully"));
        } else {
            return ResponseEntity.status(400).body(Map.of("message", "Invalid or expired token"));
        }
    }
}
