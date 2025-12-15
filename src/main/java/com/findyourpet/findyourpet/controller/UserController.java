package com.findyourpet.findyourpet.controller;

import com.findyourpet.findyourpet.model.User;
import com.findyourpet.findyourpet.service.UserService;
import org.springframework.web.bind.annotation.*;

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
}
