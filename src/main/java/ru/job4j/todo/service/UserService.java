package ru.job4j.todo.service;

import java.util.Optional;
import ru.job4j.todo.model.User;

public interface UserService {

    Optional<User> save(User user);

    Optional<User> findByLoginAndPassword(String login, String password);

    Optional<User> findById(int id);

}