package ru.job4j.todo.persistence;

import java.util.List;
import ru.job4j.todo.model.Priority;

public interface PriorityStore {

    Priority findByPosition(int position);

    List<Priority> findAll();

}