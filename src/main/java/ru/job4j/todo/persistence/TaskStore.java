package ru.job4j.todo.persistence;

import java.util.Collection;
import java.util.Optional;
import ru.job4j.todo.model.Task;

public interface TaskStore {

    Task save(Task task);

    Optional<Task> findById(int id);

    Collection<Task> findAll();

    Collection<Task> findDone();

    Collection<Task> findNew();

    boolean update(Task task);

    boolean getDone(int id);

    boolean delete(int id);

}