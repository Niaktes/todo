package ru.job4j.todo.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;
import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "tasks")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    @EqualsAndHashCode.Include
    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "priority_id")
    private Priority priority;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tasks_categories",
            joinColumns = { @JoinColumn(name = "task_id") },
            inverseJoinColumns = { @JoinColumn(name = "category_id") }
    )
    private Set<Category> categories = new HashSet<>();

    @DateTimeFormat(pattern = "HH:mm yyyy-MM-dd")
    private LocalDateTime created = LocalDateTime.now();

    private boolean done;

    public String getCreatedWithTimezone() {
        String userTimezone = user.getTimezone() != null
                ? user.getTimezone() : TimeZone.getDefault().getID();
        return created.atZone(ZoneId.systemDefault())
                .withZoneSameInstant(ZoneId.of(userTimezone))
                .format(DateTimeFormatter.ofPattern("HH:mm yyyy-MM-dd"));
    }

}