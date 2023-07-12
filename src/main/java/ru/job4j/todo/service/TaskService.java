package ru.job4j.todo.service;

import java.util.Collection;
import ru.job4j.todo.model.Task;

public interface TaskService {

    Task save(Task task);

    Task findById(int id);

    Collection<Task> findAll();

    boolean update(Task task);

    boolean delete(int id);

}