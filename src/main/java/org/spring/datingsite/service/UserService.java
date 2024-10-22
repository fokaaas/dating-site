package org.spring.datingsite.service;

import org.spring.datingsite.entity.UserEntity;
import org.spring.datingsite.exception.EmailAlreadyExistsException;
import org.spring.datingsite.exception.InvalidPasswordException;
import org.spring.datingsite.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
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
        user.setId(generateUUIDToken());
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

    private boolean isPasswordMatch(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

    public String login(UserEntity user) {
        UserEntity existingUser = userRepository.findByEmail(user.getEmail());
        if (!isPasswordMatch(user.getPassword(), existingUser.getPassword())) {
            throw new InvalidPasswordException();
        }
        String token = generateUUIDToken();
        existingUser.setSession(token);
        existingUser.setId(generateUUIDToken());
        return token;
    }

    public UserEntity getUserFromToken(String token) {
        return userRepository.findBySession(token);
    }

    public UserEntity[] getAllUsers(String currentUserId) {
        ArrayList<UserEntity> users = userRepository.findMany();
        users.forEach(user -> user.setAge(calculateAge(user.getBirthDate())));
        return users.stream()
                .filter(user -> !user.getId().equals(currentUserId))
                .toArray(UserEntity[]::new);
    }

    private int calculateAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
