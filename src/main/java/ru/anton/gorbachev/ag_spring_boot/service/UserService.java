package ru.anton.gorbachev.ag_spring_boot.service;

import ru.anton.gorbachev.ag_spring_boot.models.User;

import java.util.List;

public interface UserService extends Service<User> {
    List<User> allUsers();
    void addUser(User object);
    void deleteUser(User object);
    void editUser(User object);
    User findUserById(Long id);
    boolean isExist(String login);
}
