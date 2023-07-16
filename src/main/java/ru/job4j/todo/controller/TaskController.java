package ru.job4j.todo.controller;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.TaskService;

@Controller
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/all")
    public String getAllTasks(Model model) {
        Collection<Task> tasks = taskService.findAll().stream()
                .sorted(Comparator.comparing(Task::getId))
                .toList();
        model.addAttribute("tasks", tasks);
        return "tasks/list";
    }

    @GetMapping("/done")
    public String getDoneTasks(Model model) {
        Collection<Task> tasks = taskService.findAll().stream()
                .filter(Task::isDone)
                .sorted(Comparator.comparing(Task::getId))
                .toList();
        model.addAttribute("tasks", tasks);
        return "tasks/list";
    }

    @GetMapping("/new")
    public String getNewTasks(Model model) {
        LocalDate afterDate = LocalDate.now().minusWeeks(1);
        Collection<Task> tasks = taskService.findAll().stream()
                .filter(t -> t.getCreated().isAfter(afterDate))
                .sorted(Comparator.comparing(Task::getId))
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

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        model.addAttribute("task", taskService.findById(id));
        return "tasks/one";
    }

    @PostMapping("/delete/{id}")
    public String delete(Model model, @PathVariable int id) {
        boolean isDeleted = taskService.delete(id);
        if (!isDeleted) {
            model.addAttribute("message", "Ошибка при удалении задачи");
            return "errors/404";
        }
        return "redirect:/tasks/all";
    }

    @PostMapping("/getDone")
    public String getDone(@ModelAttribute Task task, Model model) {
        task.setDone(true);
        boolean isUpdated = taskService.update(task);
        if (!isUpdated) {
            model.addAttribute("message", "Ошибка при обновлении задачи");
            return "errors/404";
        }
        model.addAttribute("task", task);
        return "redirect:/tasks/all";
    }

    @GetMapping("/edit/{id}")
    public String getUpdateById(Model model, @PathVariable int id) {
        model.addAttribute("task", taskService.findById(id));
        return "tasks/edit";
    }

    @PostMapping("/edit")
    public String editTask(@ModelAttribute Task task, Model model) {
        boolean isUpdated = taskService.update(task);
        if (!isUpdated) {
            model.addAttribute("message", "Ошибка при обновлении задачи");
            return "errors/404";
        }
        return "redirect:/tasks/all";
    }

}