package com.vitalii.uapp_test_task.service;

import com.vitalii.uapp_test_task.command.create.ColumnCreateCommand;
import com.vitalii.uapp_test_task.command.create.TaskCreateCommand;
import com.vitalii.uapp_test_task.dto.ColumnDto;
import com.vitalii.uapp_test_task.entity.Column;
import com.vitalii.uapp_test_task.entity.Task;
import com.vitalii.uapp_test_task.exception.ResourceNotFoundException;
import com.vitalii.uapp_test_task.repository.ColumnRepository;
import com.vitalii.uapp_test_task.repository.TaskRepository;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ColumnServiceTest {

  @Mock
  private ColumnRepository columnRepository;

  @Mock
  private TaskRepository taskRepository;

  @InjectMocks
  private ColumnService columnService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testCreate() {
    ColumnCreateCommand command = new ColumnCreateCommand("name", new HashSet<>());

    Column savedColumn = new Column(command.getName());
    savedColumn.setId(1L);
    when(columnRepository.save(any(Column.class))).thenReturn(savedColumn);

    ColumnDto result = columnService.create(command);

    assertNotNull(result);
    assertEquals(savedColumn.getId(), result.getId());
    assertEquals(savedColumn.getName(), result.getName());
    verify(columnRepository, times(1)).save(any(Column.class));
  }

  @Test
  void testGetById_existingId() {
    Long id = 1L;
    Column column = new Column("Test Column");
    column.setId(id);
    when(columnRepository.findById(id)).thenReturn(Optional.of(column));

    Column result = columnService.getById(id);

    assertNotNull(result);
    assertEquals(column.getId(), result.getId());
    assertEquals(column.getName(), result.getName());
    verify(columnRepository, times(1)).findById(id);
  }

  @Test
  void testGetById_nonExistingId() {
    Long id = 1L;
    when(columnRepository.findById(id)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> columnService.getById(id));
    verify(columnRepository, times(1)).findById(id);
  }

  @Test
  void testUpdate() {
    Long id = 1L;
    ColumnCreateCommand command = new ColumnCreateCommand("Updated Column", new HashSet<>(1));
    Column column = new Column("Test Column");
    column.setId(id);
    when(columnRepository.findById(id)).thenReturn(Optional.of(column));
    when(columnRepository.save(any(Column.class))).thenReturn(column);

    ColumnDto result = columnService.update(id, command);

    assertNotNull(result);
    assertEquals(column.getId(), result.getId());
    assertEquals(command.getName(), result.getName());
    verify(columnRepository, times(1)).findById(id);
    verify(columnRepository, times(1)).save(column);
  }

  @Test
  void testGetAll() {
    Column column1 = new Column("Column 1");
    Column column2 = new Column("Column 2");
    List<Column> columns = Arrays.asList(column1, column2);
    when(columnRepository.findAll()).thenReturn(columns);

    List<ColumnDto> result = columnService.getAll();

    assertNotNull(result);
    assertEquals(columns.size(), result.size());
    verify(columnRepository, times(1)).findAll();
  }

  @Test
  void testDelete() {
    Long id = 1L;
    Column column = new Column("Test Column");
    column.setId(id);
    when(columnRepository.findById(id)).thenReturn(Optional.of(column));

    columnService.delete(id);

    verify(columnRepository, times(1)).delete(column);
  }

  @Test
  void testAddTaskToColumn() {
    Long columnId = 1L;
    Long taskId = 1L;

    Column column = new Column("Test Column");
    column.setId(columnId);

    Task task = new Task(new TaskCreateCommand("name", "description"));
    task.setId(taskId);

    when(columnRepository.findById(columnId)).thenReturn(Optional.of(column));
    when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
    when(columnRepository.save(column)).thenReturn(column);

    ColumnDto result = columnService.addTaskToColumn(columnId, taskId);

    assertNotNull(result);
    assertTrue(column.getTasks().contains(task));
    verify(columnRepository, times(1)).findById(columnId);
    verify(taskRepository, times(1)).findById(taskId);
    verify(columnRepository, times(1)).save(column);
  }
}