package com.wmu.thatsmyspot.controller;

import com.wmu.thatsmyspot.entity.User;
import com.wmu.thatsmyspot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> req) {
        String name = req.get("name");
        String email = req.get("email");
        String password = req.get("password");
        if (userService.findByEmail(email).isPresent()) {
            return ResponseEntity.badRequest().body("Email already registered");
        }
        User user = userService.registerUser(name, email, password);
        return ResponseEntity.ok(Map.of("id", user.getId(), "email", user.getEmail()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> req) {
        String email = req.get("email");
        String password = req.get("password");
        return userService.findByEmail(email)
                .filter(user -> userService.checkPassword(user, password))
                .map(user -> ResponseEntity.ok(Map.of("id", user.getId(), "email", user.getEmail(), "role", user.getRole())))
                .orElseGet(() -> ResponseEntity.status(401).body(Map.of("error", "Invalid credentials")));
    }
} 