package ru.job4j.todo.service;

import java.util.Collection;
import java.util.Optional;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;

public interface TaskService {

    Task save(Task task, User user);

    Optional<Task> findById(int id);

    Collection<Task> findAllForUser(User user);

    Collection<Task> findDoneForUser(User user);

    Collection<Task> findNewForUser(User user);

    boolean update(Task task, User user);

    boolean getDone(int id);

    boolean delete(int id);

}