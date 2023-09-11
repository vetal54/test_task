package com.vitalii.uapp_test_task.controller;

import com.vitalii.uapp_test_task.dto.TaskDto;
import com.vitalii.uapp_test_task.command.create.TaskCreateCommand;
import com.vitalii.uapp_test_task.service.TaskService;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

  private static final Logger LOG = Logger.getLogger(TaskController.class.getName());

  private final TaskService taskService;

  @PostMapping
  public ResponseEntity<TaskDto> create(@RequestBody TaskCreateCommand command) {
    LOG.info("Creating new task");
    return ResponseEntity.status(HttpStatus.CREATED).body(taskService.create(command));
  }

  @GetMapping("/{id}")
  public ResponseEntity<TaskDto> getById(@PathVariable Long id) {
    LOG.info("Getting task by id: " + id);
    return ResponseEntity.ok(new TaskDto(taskService.getById(id)));
  }

  @PutMapping("/{id}/update")
  public ResponseEntity<TaskDto> update(@PathVariable Long id,
      @RequestBody TaskCreateCommand command) {
    LOG.info("Updating task with id: " + id);
    return ResponseEntity.ok(taskService.update(id, command));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    LOG.info("Deleting task with id: " + id);
    taskService.delete(id);
    return ResponseEntity.ok().build();
  }

  @PatchMapping("/{id}/changeOrder")
  public ResponseEntity<TaskDto> changeOrder(@PathVariable Long id, int position) {
    LOG.info("Changing order for task with id: " + id + " new position: " + position);
    return ResponseEntity.ok(taskService.changeOrder(id, position));
  }
}
