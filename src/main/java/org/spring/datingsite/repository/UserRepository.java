package org.spring.datingsite.repository;

import org.spring.datingsite.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class UserRepository {
    private final ArrayList<UserEntity> users = new ArrayList<>(); // db mock

    public UserEntity findByEmail(String email) {
        for (UserEntity user : users) {
            if (user != null && user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    public void create(UserEntity user) {
        users.add(user);
    }

    public ArrayList<UserEntity> findMany() {
        return users;
    }

    public UserEntity findBySession(String session) {
        for (UserEntity user : users) {
            if (user.getSession().equals(session)) {
                return user;
            }
        }
        return null;
    }
}
