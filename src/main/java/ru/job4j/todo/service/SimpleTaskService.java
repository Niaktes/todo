package ru.job4j.todo.service;

import java.util.Collection;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.persistence.TaskStore;

@Service
@AllArgsConstructor
public class SimpleTaskService implements TaskService {

    private final TaskStore taskStore;

    @Override
    public Task save(Task task) {
        return taskStore.save(task);
    }

    @Override
    public Task findById(int id) {
        return taskStore.findById(id);
    }

    @Override
    public Collection<Task> findAll() {
        return taskStore.findAll();
    }

    @Override
    public boolean update(Task task) {
        return taskStore.update(task);
    }

    @Override
    public boolean delete(int id) {
        return taskStore.delete(id);
    }

}