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
    public Collection<Task> findAllForUser(User user) {
        return taskStore.findAllByUserId(user.getId());
    }

    @Override
    public Collection<Task> findDoneForUser(User user) {
        return taskStore.findDoneByUserId(user.getId());
    }

    @Override
    public Collection<Task> findNewForUser(User user) {
        return taskStore.findNewByUserId(user.getId());
    }

    @Override
    public boolean update(Task task, User user) {
        task.setUser(user);
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