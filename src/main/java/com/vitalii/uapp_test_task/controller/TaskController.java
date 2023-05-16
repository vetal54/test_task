package com.vitalii.uapp_test_task.controller;

import com.vitalii.uapp_test_task.dto.TaskDTO;
import com.vitalii.uapp_test_task.service.TaskService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
public class TaskController {

  private final TaskService taskService;

  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  @PostMapping
  public ResponseEntity<TaskDTO> create(@RequestBody TaskDTO taskDTO) {
    TaskDTO createdTask = taskService.create(taskDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
  }

  @GetMapping("/{id}")
  public ResponseEntity<TaskDTO> getById(@PathVariable Long id) {
    TaskDTO task = taskService.getById(id);
    return ResponseEntity.ok(task);
  }

  @GetMapping
  public ResponseEntity<List<TaskDTO>> getAll() {
    List<TaskDTO> tasks = taskService.getAll();
    return ResponseEntity.ok(tasks);
  }

  @PutMapping("/{id}/{columnId}")
  public ResponseEntity<TaskDTO> update(@PathVariable Long id, @RequestBody TaskDTO taskDTO,
      @PathVariable Long columnId) {
    TaskDTO updated = taskService.update(id, taskDTO, columnId);
    return ResponseEntity.ok(updated);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    taskService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
