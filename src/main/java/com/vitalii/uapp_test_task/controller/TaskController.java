package com.vitalii.uapp_test_task.controller;

import com.vitalii.uapp_test_task.entity.Task;
import com.vitalii.uapp_test_task.service.TaskService;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
public class TaskController extends AbstractController<Task, TaskService> {

  public TaskController(TaskService taskService) {
    super(taskService);
  }

  @GetMapping("/tasks")
  public List<Task> readAllById(@RequestParam List<UUID> ids) {
    return service.findAllById(ids);
  }

  @PutMapping("/move/{id}/foreignKey")
  public ResponseEntity<Task> updateColumnId(@PathVariable UUID id,
      @RequestParam UUID newForeignKey) {
    Task taskResult = service.moveColumn(id, newForeignKey);
    return ResponseEntity.ok().body(taskResult);
  }

  @PutMapping("/change/{id1}/{id2}")
  public ResponseEntity<Object> changeIndex(@PathVariable UUID id1, @PathVariable UUID id2) {
    Map<String, Object> map = service.changeTaskIndex(id1, id2);
    return ResponseEntity.ok().body(map);
  }
}
