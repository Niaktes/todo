package ru.job4j.todo.persistence;

import java.util.Collection;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;

@Repository
@AllArgsConstructor
public class HibernateCategoryStore implements CategoryStore {

    private final CrudStore crudStore;

    /**
     * Получить все категории из базы данных.
     * @return список категорий.
     */
    @Override
    public Collection<Category> findAll() {
        return crudStore.query("FROM Category", Category.class);
    }

}