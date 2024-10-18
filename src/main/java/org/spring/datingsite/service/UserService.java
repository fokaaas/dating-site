package org.spring.datingsite.service;

import org.spring.datingsite.entity.UserEntity;
import org.spring.datingsite.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String registerUser(UserEntity user) {
        UserEntity existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            throw new IllegalArgumentException("User with email " + user.getEmail() + " already exists.");
        }
        String token = generateUUIDToken();
        user.setSession(token);
        userRepository.create(user);
        return token;
    }

    private String generateUUIDToken() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
