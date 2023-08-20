package ru.job4j.todo.service;

import java.util.Collection;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.persistence.CategoryStore;

@Service
@AllArgsConstructor
public class SimpleCategoryService implements CategoryService {

    CategoryStore categoryStore;

    @Override
    public Collection<Category> findAll() {
        return categoryStore.findAll();
    }

}