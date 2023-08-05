package com.vitalii.uapp_test_task.service;

import com.vitalii.uapp_test_task.dto.ColumnDto;
import com.vitalii.uapp_test_task.entity.Column;
import com.vitalii.uapp_test_task.command.create.ColumnCreateCommand;
import com.vitalii.uapp_test_task.entity.Task;
import com.vitalii.uapp_test_task.exception.ResourceOverflowException;
import com.vitalii.uapp_test_task.exception.ResourceNotFoundException;
import com.vitalii.uapp_test_task.repository.ColumnRepository;
import com.vitalii.uapp_test_task.repository.TaskRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ColumnService {

  private static final Logger LOG = Logger.getLogger(ColumnService.class.getName());

  private final ColumnRepository columnRepository;
  private final TaskRepository taskRepository;

  public ColumnDto create(ColumnCreateCommand command) {
    LOG.info("Saving column: " + command.toString());

    Column column = new Column(command.getName());
    column.setColumnIndex(calculateColumnIndex());
    Column savedColumn = columnRepository.save(column);

    LOG.info("Saved column with id: " + savedColumn.getId());
    return new ColumnDto(savedColumn);
  }

  public Column getById(Long id) {
    LOG.info("Fetching column by id: " + id);

    Column column = columnRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Column not found with id: " + id));

    LOG.info("Retrieved column with id: " + id);
    return column;
  }

  public ColumnDto update(Long id, ColumnCreateCommand command) {
    LOG.info("Updating column with id: " + id);

    Column column = getById(id);
    column.setName(command.getName());
    List<Task> tasks = new ArrayList<>();
    command.getTaskIds().forEach(
        taskId -> tasks.add(getTaskById(taskId)));
    column.setTasks(tasks);
    Column updatedColumn = columnRepository.save(column);

    LOG.info("Updated column with id: " + updatedColumn.getId());
    return new ColumnDto(updatedColumn);
  }

  public List<ColumnDto> getAll() {
    LOG.info("Fetching all columns");

    List<ColumnDto> columns = columnRepository.findAll()
        .stream()
        .sorted(Comparator.comparing(Column::getColumnIndex))
        .map(ColumnDto::new)
        .collect(Collectors.toList());

    LOG.info("Retrieved " + columns.size() + " columns");
    return columns;
  }

  public void delete(Long id) {
    Column column = getById(id);

    LOG.info("Deleting column with id: " + id);

    columnRepository.delete(column);
  }

  public ColumnDto addTaskToColumn(Long id, Long taskId) {
    LOG.info("Adding task with id: " + taskId + " to column with id: " + id);

    Column column = getById(id);
    Task task = getTaskById(taskId);
    task.setTaskIndex(calculateTaskIndex(column.getId()));
    task.setColumn(column);
    column.getTasks().add(task);
    Column updatedColumn = columnRepository.save(column);

    LOG.info("Task with id: " + taskId + "has been added to the column with id: " + id);
    return new ColumnDto(updatedColumn);
  }

  public Task getTaskById(Long id) {
    LOG.info("Fetching task by id: " + id);

    Task task = taskRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

    LOG.info("Retrieved task with id: " + id);
    return task;
  }

  private int calculateColumnIndex() {
    int size = columnRepository.findAll().size();
    if (size < 20) {
      int power = size + 11;
      int result = (int) Math.pow(2, power);
      LOG.info("Column index calculated: " + result);
      return result;
    } else {
      String errorMessage = "Unable to save new column, no free space";
      LOG.warning(errorMessage);
      throw new ResourceOverflowException(errorMessage);
    }
  }

  private int calculateTaskIndex(long columnId) {
    int size = taskRepository.findAllByColumnId(columnId).size();
    if (size < 20) {
      int power = size + 11;
      int result = (int) Math.pow(2, power);
      LOG.info("Task index calculated: " + result);
      return result;
    } else {
      String errorMessage = "The task cannot be placed in the column with id: " + columnId;
      LOG.warning(errorMessage);
      throw new ResourceOverflowException(errorMessage);
    }
  }
}
