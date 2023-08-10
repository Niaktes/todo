package ru.job4j.todo.persistence;

import java.util.Collection;
import ru.job4j.todo.model.Priority;

public interface PriorityStore {

    Collection<Priority> findAll();

}