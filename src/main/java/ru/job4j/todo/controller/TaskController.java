package ru.job4j.todo.controller;

import java.time.LocalDate;
import java.util.Collection;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.TaskService;

@Controller
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/all")
    public String getAllTasks(Model model) {
        Collection<Task> tasks = taskService.findAll();
        model.addAttribute("tasks", tasks);
        return "tasks/list";
    }

    @GetMapping("/done")
    public String getDoneTasks(Model model) {
        Collection<Task> tasks = taskService.findAll().stream()
                .filter(Task::isDone)
                .toList();
        model.addAttribute("tasks", tasks);
        return "tasks/list";
    }

    @GetMapping("/new")
    public String getNewTasks(Model model) {
        LocalDate afterDate = LocalDate.now().minusWeeks(1);
        Collection<Task> tasks = taskService.findAll().stream()
                .filter(t -> t.getCreated().isAfter(afterDate))
                .toList();
        model.addAttribute("tasks", tasks);
        return "tasks/list";
    }

    @GetMapping("/create")
    public String getCreationPage(Model model) {
        return "tasks/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Task task, Model model) {
        taskService.save(task);
        return "redirect:/tasks/all";
    }

}