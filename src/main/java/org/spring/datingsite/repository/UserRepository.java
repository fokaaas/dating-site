package org.spring.datingsite.repository;

import org.spring.datingsite.entity.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    private final UserEntity[] users = new UserEntity[100]; // db mock

    public UserEntity findByEmail(String email) {
        for (UserEntity user : users) {
            if (user != null && user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    public void create(UserEntity user) {
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) {
                System.out.println("User created: " + user.getEmail());
                users[i] = user;
                return;
            }
        }
    }
}
