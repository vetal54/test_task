package com.vitalii.uapp_test_task.controller;

import com.vitalii.uapp_test_task.entity.Task;
import com.vitalii.uapp_test_task.service.TaskService;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
public class TaskController extends AbstractController<Task, TaskService> {

  public TaskController(TaskService taskService) {
    super(taskService);
  }

  @GetMapping("/allTasksById/{id}")
  public List<Task> readAllById(@PathVariable UUID id) {
    return service.findAllById(id);
  }
}
