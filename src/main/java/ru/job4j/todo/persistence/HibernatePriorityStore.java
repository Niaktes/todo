package ru.job4j.todo.persistence;

import java.util.Collection;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Priority;

@Repository
@AllArgsConstructor
public class HibernatePriorityStore implements PriorityStore {

    private final CrudStore crudStore;

    /**
     * Получить все приоритеты из базы данных.
     * @return список приоритетов.
     */
    @Override
    public Collection<Priority> findAll() {
        return crudStore.query("FROM Priority", Priority.class);
    }

}