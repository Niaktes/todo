package ru.job4j.todo.service;

import java.util.Collection;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.persistence.TaskStore;

@Service
@AllArgsConstructor
public class SimpleTaskService implements TaskService {

    private final TaskStore taskStore;

    @Override
    public Task save(Task task, User user) {
        task.setUser(user);
        return taskStore.save(task);
    }

    @Override
    public Optional<Task> findById(int id) {
        return taskStore.findById(id);
    }

    @Override
    public Collection<Task> findAll() {
        return taskStore.findAll();
    }

    @Override
    public Collection<Task> findDone() {
        return taskStore.findDone();
    }

    @Override
    public Collection<Task> findNew() {
        return taskStore.findNew();
    }

    @Override
    public boolean update(Task task) {
        if (task.getUser() == null) {
            Optional<Task> oldTaskOptional = taskStore.findById(task.getId());
            if (oldTaskOptional.isEmpty()) {
                return false;
            }
            task.setUser(oldTaskOptional.get().getUser());
        }
        return taskStore.update(task);
    }

    @Override
    public boolean getDone(int id) {
        return taskStore.getDone(id);
    }

    @Override
    public boolean delete(int id) {
        return taskStore.delete(id);
    }

}