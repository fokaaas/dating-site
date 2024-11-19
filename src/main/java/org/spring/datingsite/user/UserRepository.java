package org.spring.datingsite.user;

import org.spring.datingsite.user.entity.UserEntity;
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

    public void update(UserEntity updatedUser) {
        for (int i = 0; i < users.size(); i++) {
            UserEntity user = users.get(i);
            if (user.getId().equals(updatedUser.getId())) {
                users.set(i, updatedUser);
                return;
            }
        }
        throw new IllegalArgumentException("User with ID " + updatedUser.getId() + " not found.");
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

    public UserEntity findById(String id) {
        for (UserEntity user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }
}
