package ru.job4j.todo.persistence;

import java.util.Collection;
import ru.job4j.todo.model.Category;

public interface CategoryStore {

    Collection<Category> findAll();

}