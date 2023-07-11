package ru.job4j.todo.model;

import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "tasks")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Task {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    @EqualsAndHashCode.Include
    private String name;

    private String description;

    private LocalDateTime created;

    private boolean done;

}