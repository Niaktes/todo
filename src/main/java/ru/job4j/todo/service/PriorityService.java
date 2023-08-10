package ru.job4j.todo.service;

import java.util.Collection;
import ru.job4j.todo.model.Priority;

public interface PriorityService {

    Collection<Priority> findAll();

}