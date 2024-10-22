package org.spring.datingsite.service;

import org.spring.datingsite.entity.UserEntity;
import org.spring.datingsite.exception.EmailAlreadyExistsException;
import org.spring.datingsite.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String createUser(UserEntity user) {
        UserEntity existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            throw new EmailAlreadyExistsException(user.getEmail());
        }
        String token = generateUUIDToken();
        user.setSession(token);
        user.setPassword(hashPassword(user.getPassword()));
        userRepository.create(user);
        return token;
    }

    private String generateUUIDToken() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
