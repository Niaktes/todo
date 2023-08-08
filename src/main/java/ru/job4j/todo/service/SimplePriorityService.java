package ru.job4j.todo.service;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.persistence.PriorityStore;

@Service
@AllArgsConstructor
public class SimplePriorityService implements PriorityService {

    private final PriorityStore priorityStore;

    @Override
    public Priority findByPosition(int position) {
        return priorityStore.findByPosition(position);
    }

    @Override
    public List<Priority> findAll() {
        return priorityStore.findAll();
    }

}