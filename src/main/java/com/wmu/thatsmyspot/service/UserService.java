package com.wmu.thatsmyspot.service;

import com.wmu.thatsmyspot.entity.User;
import com.wmu.thatsmyspot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User registerUser(String name, String email, String rawPassword) {
        User user = User.builder()
                .name(name)
                .email(email)
                .passwordHash(passwordEncoder.encode(rawPassword))
                .role("USER")
                .build();
        return userRepository.save(user);
    }

    public boolean checkPassword(User user, String rawPassword) {
        return passwordEncoder.matches(rawPassword, user.getPasswordHash());
    }
} 