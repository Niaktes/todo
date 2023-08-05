package ru.job4j.todo.persistence;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

@Repository
@AllArgsConstructor
public class HibernateTaskStore implements TaskStore {

    private final CrudStore crudStore;

    /**
     * Сохранить задачу в базе данных.
     * @param task задача.
     * @return задача с ID.
     */
    @Override
    public Task save(Task task) {
        crudStore.run(session -> session.save(task));
        return task;
    }

    /**
     * Найти задачу по ID.
     * @param id ID задачи.
     * @return Optional задачи.
     */
    @Override
    public Optional<Task> findById(int id) {
        return crudStore.optional("FROM Task WHERE id = :tId", Task.class, Map.of("tId", id));
    }

    /**
     * Получить список всех задач конкретного пользователя.
     * @param id ID пользователя.
     * @return список задач.
     */
    @Override
    public Collection<Task> findAllByUserId(int id) {
        return crudStore.query("FROM Task WHERE user_id = :uId", Task.class, Map.of("uId", id));
    }

    /**
     * Получить выполненные задачи конкретного пользователя.
     * @param id ID пользователя.
     * @return список задач.
     */
    @Override
    public Collection<Task> findDoneByUserId(int id) {
        return crudStore.query("FROM Task WHERE done = true AND user_id = :uId", Task.class, Map.of("uId", id));
    }

    /**
     * Получить задачи не старше одной недели для конкретного пользователя.
     * @param id ID пользователя
     * @return список задач.
     */
    @Override
    public Collection<Task> findNewByUserId(int id) {
        LocalDate afterDate = LocalDate.now().minusWeeks(1);
        return crudStore.query(
                "FROM Task WHERE created > :afterDate AND user_id = :uId",
                Task.class,
                Map.of("afterDate", afterDate, "uId", id));
    }

    /**
     * Обновить в базе данных задачу.
     * @param task задача.
     * @return true если удалось обновить.
     */
    @Override
    public boolean update(Task task) {
        return crudStore.run(session -> session.update(task));
    }

    /**
     * Отметить задачу выполненной.
     * @param id ID задачи.
     * @return true если удалось обновить.
     */
    @Override
    public boolean getDone(int id) {
        return crudStore.run("UPDATE Task SET done = true WHERE id = :tId", Map.of("tId", id));
    }

    /**
     * Удалить задачу
     * @param id ID задачи.
     * @return true если удалось удалить.
     */
    @Override
    public boolean delete(int id) {
        return crudStore.run("DELETE Task WHERE id = :tId", Map.of("tId", id));
    }

}