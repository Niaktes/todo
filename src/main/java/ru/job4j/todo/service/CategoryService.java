package ru.job4j.todo.service;

import java.util.Collection;
import ru.job4j.todo.model.Category;

public interface CategoryService {

    Collection<Category> findAll();

}