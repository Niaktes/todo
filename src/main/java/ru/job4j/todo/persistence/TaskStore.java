package ru.job4j.todo.persistence;

import java.util.Collection;
import ru.job4j.todo.model.Task;

public interface TaskStore {

    Task save(Task task);

    Task findById(int id);

    Collection<Task> findAll();

    boolean update(Task task);

    boolean delete(int id);

}