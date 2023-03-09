package com.vitalii.uapp_test_task.service;

import com.vitalii.uapp_test_task.entity.Column;
import com.vitalii.uapp_test_task.entity.Task;
import com.vitalii.uapp_test_task.exception.ResourceNotFoundException;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TaskServiceTest {

  @Autowired
  private TaskService taskService;
  @Autowired
  private ColumnService columnService;

  @Test
  void save() {
    Task task = createTask();
    Task saveTask = taskService.save(task);

    Assertions.assertEquals(task, saveTask);
  }

  @Test
  void update() {
    Task task = taskService.save(createTask());
    task.setName("New test name");

    Task updateTask = taskService.update(task);

    Assertions.assertAll(
        () -> Assertions.assertEquals("New test name", updateTask.getName()),
        () -> Assertions.assertEquals(task, updateTask)
    );
  }

  @Test
  void getById() {
    Task task = taskService.save(createTask());
    Task findByIdTask = taskService.findById(task.getId());

    Assertions.assertEquals(task, findByIdTask);
  }

  @Test
  void findByIdFalse() {
    UUID randomId = UUID.randomUUID();

    Assertions.assertThrows(ResourceNotFoundException.class,
        () -> taskService.findById(randomId));
  }

  @Test
  void getAllByIds() {
    Task firstTask = taskService.save(createTask());

    Task secondTask = new Task();
    secondTask.setName("Second task");
    secondTask.setDescription("description");
    secondTask.setTaskIndex(firstTask.getTaskIndex() + 1);
    secondTask.setColumnId(firstTask.getColumnId());
    Task saveSecondTask = taskService.save(secondTask);

    List<UUID> ids = List.of(firstTask.getId(), secondTask.getId());
    List<Task> tasks = taskService.findAllById(ids);

    Assertions.assertAll(
        () -> Assertions.assertEquals(2, tasks.size()),
        () -> Assertions.assertTrue(tasks.contains(firstTask)),
        () -> Assertions.assertTrue(tasks.contains(saveSecondTask))
    );
  }

  @Test
  void moveColumn() {
    Task task = taskService.save(createTask());
    Column newColumn = createColumn();

    Task updateTask = taskService.moveColumn(task.getId(), newColumn.getId());

    Assertions.assertEquals(newColumn.getId(), updateTask.getColumnId());
  }

  private Task createTask() {
    Task task = new Task();
    task.setName("Test name");
    task.setDescription("Test description");
    task.setTaskIndex(0);
    task.setColumnId(createColumn().getId());
    return task;
  }

  private Column createColumn() {
    Column column = new Column();
    column.setName("Test");
    column.setColumnIndex(0);
    return columnService.save(column);
  }
}