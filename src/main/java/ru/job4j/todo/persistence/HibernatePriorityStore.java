package ru.job4j.todo.persistence;

import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Priority;

@Repository
@AllArgsConstructor
public class HibernatePriorityStore implements PriorityStore {

    private final CrudStore crudStore;

    /**
     * Найти приоритет по номеру в иерархии.
     * @param position номер важности.
     * @return Priority.
     */
    @Override
    public Priority findByPosition(int position) {
        return crudStore.getOne("FROM Priority WHERE position = :pPosition",
                Priority.class,
                Map.of("pPosition", position));
    }

    /**
     * Получить список всех приоритетов.
     * @return список приоритетов.
     */
    @Override
    public List<Priority> findAll() {
        return crudStore.query("FROM Priority", Priority.class);
    }

}