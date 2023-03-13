package com.neighborstan.todo.controller;

import com.neighborstan.todo.domain.Task;
import com.neighborstan.todo.service.TaskService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.Objects.isNull;


@Controller
@RequestMapping("/")
@Data
public class TaskController {

    public static final int PAGE = 1;
    public static final int LIMIT = 5;
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public String tasks(Model model,
                            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                            @RequestParam(value = "limit", required = false, defaultValue = "5") int limit) {

        List<Task> tasks = taskService.getAll((page - PAGE) * limit, limit);
        model.addAttribute("tasks", tasks);
        model.addAttribute("current_page", page);
        int totalPages = (int) Math.ceil(1.0 * taskService.getAllCount() / limit);
        if (totalPages > PAGE) {
            List<Integer> pageNumbers = IntStream.rangeClosed(PAGE, totalPages).boxed().toList();
            model.addAttribute("page_numbers", pageNumbers);
            model.addAttribute("limit", LIMIT);
        }
        return "tasks";
    }

    @PostMapping("/{id}")
    public String edit(Model model,
                     @PathVariable Integer id,
                     @RequestBody TaskInfo info) {

        if (isNull(id) || id <= 0) {
            throw new RuntimeException("Invalid id");
        }

        taskService.edit(id, info.getDescription(), info.getStatus());
        return tasks(model, PAGE, LIMIT);
    }

    @PostMapping
    public String add(Model model,
                    @RequestBody TaskInfo info) {

        taskService.create(info.getDescription(), info.getStatus());
        return tasks(model, PAGE, LIMIT);
    }

    @DeleteMapping("/{id}")
    public String delete(Model model,
                     @PathVariable Integer id) {

        if (isNull(id) || id <= 0) {
            throw new RuntimeException("Invalid id");
        }
        taskService.delete(id);

        return tasks(model, PAGE, LIMIT);
    }
}
