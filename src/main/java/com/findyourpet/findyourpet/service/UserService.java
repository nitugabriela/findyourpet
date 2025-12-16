package com.findyourpet.findyourpet.service;

import com.findyourpet.findyourpet.model.User;
import com.findyourpet.findyourpet.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.time.LocalDateTime;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(User user) {
        if (repository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("USER");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    public Optional<User> login(String email, String password) {
        return repository.findByEmail(email)
                .filter(u -> passwordEncoder.matches(password, u.getPassword()));
    }

    public void processForgotPassword(String email) {
        Optional<User> userOptional = repository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            String token = UUID.randomUUID().toString();

            user.setResetPasswordToken(token);
            user.setResetPasswordExpires(LocalDateTime.now().plusHours(1));
            repository.save(user);

            String resetLink = "http://localhost:5173/reset-password/" + token;

            System.out.println("========================================");
            System.out.println("PASSWORD RESET LINK: " + resetLink);
            System.out.println("========================================");
        }
    }

    public boolean processResetPassword(String token, String newPassword) {
        Optional<User> userOptional = repository.findByResetPasswordToken(token);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (user.getResetPasswordExpires().isBefore(LocalDateTime.now())) {
                return false;
            }

            user.setPassword(passwordEncoder.encode(newPassword));

            user.setResetPasswordToken(null);
            user.setResetPasswordExpires(null);

            repository.save(user);
            return true;
        }
        return false;
    }
}
