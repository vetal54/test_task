package com.vitalii.uapp_test_task.controller;

import com.vitalii.uapp_test_task.dto.ColumnDto;
import com.vitalii.uapp_test_task.command.create.ColumnCreateCommand;
import com.vitalii.uapp_test_task.service.ColumnService;
import java.util.List;
import java.util.logging.Logger;
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
@RequestMapping("/column")
@RequiredArgsConstructor
public class ColumnController {

  private static final Logger LOG = Logger.getLogger(ColumnController.class.getName());

  private final ColumnService columnService;

  @PostMapping
  public ResponseEntity<ColumnDto> create(@RequestBody ColumnCreateCommand command) {
    LOG.info("Creating a new column");
    return new ResponseEntity<>(columnService.create(command), HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ColumnDto> getById(@PathVariable Long id) {
    LOG.info("Getting column by id: " + id);
    return ResponseEntity.ok(new ColumnDto(columnService.getById(id)));
  }

  @GetMapping
  public ResponseEntity<List<ColumnDto>> getAll() {
    LOG.info("Getting all columns");
    return ResponseEntity.ok(columnService.getAll());
  }

  @PutMapping("/{id}/update")
  public ResponseEntity<ColumnDto> update(@PathVariable Long id,
      @RequestBody ColumnCreateCommand command) {
    LOG.info("Updating column with id: " + id);
    return ResponseEntity.ok(columnService.update(id, command));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    LOG.info("Deleting column with id: " + id);
    columnService.delete(id);
    return ResponseEntity.ok().build();
  }

  @PatchMapping("/{id}/addTask")
  public ResponseEntity<ColumnDto> addTaskToColumn(@PathVariable Long id, Long taskId) {
    LOG.info("Adding task with id " + taskId + " to column with id: " + id);
    return ResponseEntity.ok(columnService.addTaskToColumn(id, taskId));
  }
}
