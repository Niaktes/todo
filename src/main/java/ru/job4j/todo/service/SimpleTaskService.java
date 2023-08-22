package ru.job4j.todo.service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.persistence.TaskStore;
import ru.job4j.todo.utilities.CategoriesUtil;
import ru.job4j.todo.utilities.TimeZoneUtil;

@Service
@AllArgsConstructor
public class SimpleTaskService implements TaskService {

    private final TaskStore taskStore;

    @Override
    public Task save(Task task, User user, Set<Integer> categoriesId) {
        task.setUser(user);
        task.setCategories(CategoriesUtil.getCategoriesWithId(categoriesId));
        return taskStore.save(task);
    }

    @Override
    public Optional<Task> findById(int id) {
        Optional<Task> taskOptional = taskStore.findById(id);
        if (taskOptional.isPresent()) {
            taskOptional = Optional.of(getTaskWithUsersTimezone(taskOptional.get()));
        }
        return taskOptional;
    }

    @Override
    public Collection<Task> findAllForUser(User user) {
        return getTasksWithUsersTimezone(taskStore.findAllByUserId(user.getId()));
    }

    @Override
    public Collection<Task> findDoneForUser(User user) {
        return getTasksWithUsersTimezone(taskStore.findDoneByUserId(user.getId()));
    }

    @Override
    public Collection<Task> findNewForUser(User user) {
        return getTasksWithUsersTimezone(taskStore.findNewByUserId(user.getId()));
    }

    @Override
    public boolean update(Task task, User user, Set<Integer> categoriesId) {
        task.setUser(user);
        task.setCategories(CategoriesUtil.getCategoriesWithId(categoriesId));
        task.setCreated(TimeZoneUtil.changeToServersTimezone(task.getCreated(), task.getUser()));
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

    private Collection<Task> getTasksWithUsersTimezone(Collection<Task> tasks) {
        return tasks.stream()
                .map(this::getTaskWithUsersTimezone)
                .toList();
    }

    private Task getTaskWithUsersTimezone(Task task) {
        LocalDateTime usersLdt = TimeZoneUtil.changeToUsersTimezone(task.getCreated(), task.getUser());
        task.setCreated(usersLdt);
        return task;
    }

}