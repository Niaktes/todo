package ru.job4j.todo.utilities;

import java.util.Set;
import java.util.stream.Collectors;
import ru.job4j.todo.model.Category;

public class CategoriesUtil {

    private CategoriesUtil() { }

    public static Set<Category> getCategoriesWithId(Set<Integer> categoriesId) {
        return categoriesId.stream().map(i -> {
            Category category = new Category();
            category.setId(i);
            return category;
        }).collect(Collectors.toSet());
    }

}