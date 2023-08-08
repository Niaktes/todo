package ru.job4j.todo.service;

import java.util.List;
import ru.job4j.todo.model.Priority;

public interface PriorityService {

    Priority findByPosition(int position);

    List<Priority> findAll();

}