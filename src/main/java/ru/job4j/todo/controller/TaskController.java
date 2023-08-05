package ru.job4j.todo.controller;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.TaskService;
import ru.job4j.todo.service.UserService;

@Controller
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;

    @GetMapping("/all")
    public String getAllTasks(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Collection<Task> tasks = taskService.findAllForUser(user).stream()
                .sorted(Comparator.comparing(Task::getId))
                .toList();
        model.addAttribute("tasks", tasks);
        return "tasks/list";
    }

    @GetMapping("/done")
    public String getDoneTasks(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Collection<Task> tasks = taskService.findDoneForUser(user);
        model.addAttribute("tasks", tasks);
        return "tasks/list";
    }

    @GetMapping("/new")
    public String getNewTasks(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Collection<Task> tasks = taskService.findNewForUser(user);
        model.addAttribute("tasks", tasks);
        return "tasks/list";
    }

    @GetMapping("/create")
    public String getCreationPage(Model model) {
        return "tasks/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Task task, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        taskService.save(task, user);
        return "redirect:/tasks/all";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        Optional<Task> taskOptional = taskService.findById(id);
        if (taskOptional.isEmpty()) {
            model.addAttribute("message", "Задача с указанным Id не найдена.");
            return "errors/404";
        }
        model.addAttribute("task", taskOptional.get());
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

    @PostMapping("/getDone/{id}")
    public String getDone(Model model, @PathVariable int id) {
        boolean isUpdated = taskService.getDone(id);
        if (!isUpdated) {
            model.addAttribute("message", "Ошибка при обновлении задачи");
            return "errors/404";
        }
        return "redirect:/tasks/all";
    }

    @GetMapping("/edit/{id}")
    public String getUpdateById(Model model, @PathVariable int id) {
        Optional<Task> taskOptional = taskService.findById(id);
        if (taskOptional.isEmpty()) {
            model.addAttribute("message", "Задача с указанным Id не найдена.");
            return "errors/404";
        }
        model.addAttribute("task", taskOptional.get());
        return "tasks/edit";
    }

    @PostMapping("/edit")
    public String editTask(@ModelAttribute Task task, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        boolean isUpdated = taskService.update(task, user);
        if (!isUpdated) {
            model.addAttribute("message", "Ошибка при обновлении задачи");
            return "errors/404";
        }
        return "redirect:/tasks/all";
    }

}