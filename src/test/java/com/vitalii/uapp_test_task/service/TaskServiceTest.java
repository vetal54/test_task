package com.vitalii.uapp_test_task.service;

import com.vitalii.uapp_test_task.command.create.ColumnCreateCommand;
import com.vitalii.uapp_test_task.command.create.TaskCreateCommand;
import com.vitalii.uapp_test_task.dto.ColumnDto;
import com.vitalii.uapp_test_task.dto.TaskDto;
import com.vitalii.uapp_test_task.entity.Task;
import com.vitalii.uapp_test_task.exception.ResourceNotFoundException;
import com.vitalii.uapp_test_task.repository.TaskRepository;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TaskServiceTest {

  @Autowired
  private TaskRepository taskRepository;
  @Autowired
  private ColumnService columnService;
  @Autowired
  private TaskService taskService;

  @Test
  void testCreate() {
    TaskCreateCommand command = new TaskCreateCommand("Task", "Description");

    TaskDto result = taskService.create(command);

    assertNotNull(result);
    assertNotNull(result.getId());
    assertEquals("Task", result.getName());
    assertEquals("Description", result.getDescription());
  }

  @Test
  void testGetById_existingId() {
    Task task = new Task(new TaskCreateCommand("Task", "Description"));
    taskRepository.save(task);

    Task result = taskService.getById(task.getId());

    assertNotNull(result);
    assertEquals(task.getId(), result.getId());
    assertEquals(task.getName(), result.getName());
  }

  @Test
  void testGetById_nonExistingId() {
    assertThrows(ResourceNotFoundException.class, () -> taskService.getById(-1L));
  }

  @Test
  void testGetAllByColumnId() {
    TaskDto task1 = taskService.create(new TaskCreateCommand("Task1", "Description1"));
    TaskDto task2 = taskService.create(new TaskCreateCommand("Task2", "Description2"));

    ColumnDto column = columnService.create(new ColumnCreateCommand("name", new HashSet<>()));

    columnService.addTaskToColumn(column.getId(), task1.getId());
    columnService.addTaskToColumn(column.getId(), task2.getId());

    List<Task> result = taskService.getAllByColumnId(column.getId());

    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals("Task1", result.get(0).getName());
    assertEquals("Task2", result.get(1).getName());
  }

  @Test
  void testUpdate() {
    TaskDto task = taskService.create(new TaskCreateCommand("Task1", "Description1"));

    TaskCreateCommand command = new TaskCreateCommand("Updated Task", "Updated Description");

    TaskDto result = taskService.update(task.getId(), command);

    assertNotNull(result);
    assertEquals(task.getId(), result.getId());
    assertEquals("Updated Task", result.getName());
    assertEquals("Updated Description", result.getDescription());

    Task updatedTask = taskRepository.findById(task.getId()).orElse(null);
    assertNotNull(updatedTask);
    assertEquals("Updated Task", updatedTask.getName());
    assertEquals("Updated Description", updatedTask.getDescription());
  }

  @Test
  void testDelete() {
    TaskDto task = taskService.create(new TaskCreateCommand("Task1", "Description1"));
    taskService.delete(task.getId());
    assertNull(taskRepository.findById(task.getId()).orElse(null));
  }

  @Test
  void testChangeOrder_noChange() {
    TaskDto task = taskService.create(new TaskCreateCommand("Task1", "Description1"));

    columnService.create(new ColumnCreateCommand("name", new HashSet<>()));

    columnService.addTaskToColumn(1L, 1L);

    TaskDto result = taskService.changeOrder(task.getId(), 0);

    assertNotNull(result);
    assertEquals(task.getId(), result.getId());
    assertEquals(2048, result.getTaskIndex());
  }

  @Test
  void testChangeOrder_startPosition() {
    TaskDto task1 = taskService.create(new TaskCreateCommand("Task1", "Description1"));
    TaskDto task2 = taskService.create(new TaskCreateCommand("Task2", "Description2"));

    columnService.create(new ColumnCreateCommand("name", new HashSet<>()));

    columnService.addTaskToColumn(1L, task1.getId());
    columnService.addTaskToColumn(1L, task2.getId());

    TaskDto result = taskService.changeOrder(task2.getId(), 0);

    assertNotNull(result);
    assertEquals(task2.getId(), result.getId());
    assertEquals(1024, result.getTaskIndex());
  }

  @Test
  void testChangeOrder_endPosition() {
    taskRepository.deleteAll();
    TaskDto task1 = taskService.create(new TaskCreateCommand("Task1", "Description1"));
    TaskDto task2 = taskService.create(new TaskCreateCommand("Task2", "Description2"));

    ColumnDto column = columnService.create(new ColumnCreateCommand("name", new HashSet<>()));

    columnService.addTaskToColumn(column.getId(), task1.getId());
    columnService.addTaskToColumn(column.getId(), task2.getId());

    TaskDto result = taskService.changeOrder(task1.getId(), 1);

    assertNotNull(result);
    assertEquals(task1.getId(), result.getId());
    assertEquals(3072, result.getTaskIndex());
  }

  @Test
  void testChangeOrder_middlePosition() {
    TaskDto task1 = taskService.create(new TaskCreateCommand("Task1", "Description1"));
    TaskDto task2 = taskService.create(new TaskCreateCommand("Task2", "Description2"));
    TaskDto task3 = taskService.create(new TaskCreateCommand("Task3", "Description3"));

    ColumnDto column = columnService.create(new ColumnCreateCommand("name", new HashSet<>()));

    columnService.addTaskToColumn(column.getId(), task1.getId());
    columnService.addTaskToColumn(column.getId(), task2.getId());
    columnService.addTaskToColumn(column.getId(), task3.getId());

    TaskDto result = taskService.changeOrder(task1.getId(), 2);

    assertNotNull(result);
    assertEquals(task1.getId(), result.getId());
    assertEquals(6144, result.getTaskIndex());
  }
}