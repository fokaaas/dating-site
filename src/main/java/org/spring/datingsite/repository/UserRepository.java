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

    private final ArrayList<UserEntity> platformUsers = new ArrayList<>(); // Mock database


    public UserRepository() {
        initializeMockUsers();
    }

    private void initializeMockUsers() {
        platformUsers.add(new UserEntity("1", "Іван", "Паляниця", "Male", "https://via.placeholder.com/150", "ivan.palyanytsya@example.com", 25, "Жмеринка"));
        platformUsers.add(new UserEntity("2", "Галя", "Іващенко", "Female", "https://via.placeholder.com/150", "galya.kruasan@example.com", 28, "Калуш"));
        platformUsers.add(new UserEntity("3", "Микола", "Сало", "Male", "https://via.placeholder.com/150", "mykola.salo@example.com", 30, "Боярка"));
        platformUsers.add(new UserEntity("4", "Марічка", "Іваненко", "Female", "https://via.placeholder.com/150", "marychka.lozhka@example.com", 24, "Буча"));
        platformUsers.add(new UserEntity("5", "Сергій", "Капуста", "Male", "https://via.placeholder.com/150", "sergiy.kapusta@example.com", 27, "Конотоп"));
        platformUsers.add(new UserEntity("6", "Оксана", "Загорулько", "Female", "https://via.placeholder.com/150", "oksana.kartoplya@example.com", 32, "Чортків"));
        platformUsers.add(new UserEntity("7", "Василь", "Загубигорілка", "Male", "https://via.placeholder.com/150", "vasyl.horilka@example.com", 26, "Покровськ"));
        platformUsers.add(new UserEntity("8", "Тетяна", "Смалець", "Female", "https://via.placeholder.com/150", "tetyana.smalets@example.com", 29, "Глухів"));
        platformUsers.add(new UserEntity("9", "Петро", "Дзиґа", "Male", "https://via.placeholder.com/150", "petro.dzyga@example.com", 31, "Бровари"));
        platformUsers.add(new UserEntity("10", "Зоя", "Соломка", "Female", "https://via.placeholder.com/150", "zoya.solomka@example.com", 30, "Ромни"));
        platformUsers.add(new UserEntity("11", "Андрій", "Морозицький", "Male", "https://via.placeholder.com/150", "andriy.moroz@example.com", 28, "Верхньодніпровськ"));
        platformUsers.add(new UserEntity("12", "Люба", "Спідовіченко", "Female", "https://via.placeholder.com/150", "lyuba.kovbasa@example.com", 26, "Диканька"));
        platformUsers.add(new UserEntity("13", "Тарас", "Горішок", "Male", "https://via.placeholder.com/150", "taras.horyshok@example.com", 29, "Чигирин"));
        platformUsers.add(new UserEntity("14", "Олена", "Вишня", "Female", "https://via.placeholder.com/150", "olena.vyshnya@example.com", 27, "Тлумач"));
        platformUsers.add(new UserEntity("15", "Олег", "Шкарпетко", "Male", "https://via.placeholder.com/150", "oleg.shkarpetko@example.com", 33, "Здолбунів"));
        platformUsers.add(new UserEntity("16", "Катерина", "Сушко", "Female", "https://via.placeholder.com/150", "kateryna.sushko@example.com", 31, "Ізюм"));
    }

    public ArrayList<UserEntity> findManyUsers() {
        return platformUsers;
    }
}
