package ru.job4j.todo.service;

import java.util.Collection;
import java.util.Optional;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;

public interface TaskService {

    Task save(Task task, User user);

    Optional<Task> findById(int id);

    Collection<Task> findAll();

    Collection<Task> findDone();

    Collection<Task> findNew();

    boolean update(Task task, User user);

    boolean getDone(int id);

    boolean delete(int id);

}