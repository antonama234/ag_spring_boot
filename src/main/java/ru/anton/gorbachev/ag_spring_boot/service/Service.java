package ru.anton.gorbachev.ag_spring_boot.service;

import java.util.List;

public interface Service<T> {
    List<T> allUsers();
    void addUser(T object);
    void deleteUser(T object);
    void editUser(T object);
    T findUserById(Long id);
    T findByLogin(String login);
    boolean isExist(String login);
}
