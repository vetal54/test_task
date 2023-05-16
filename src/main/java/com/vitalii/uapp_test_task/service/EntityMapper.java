package com.vitalii.uapp_test_task.service;

import com.vitalii.uapp_test_task.dto.ColumnDTO;
import com.vitalii.uapp_test_task.dto.TaskDTO;
import com.vitalii.uapp_test_task.entity.Column;
import com.vitalii.uapp_test_task.entity.Task;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class EntityMapper {

  public ColumnDTO toColumnDTO(Column column) {
    ColumnDTO columnDTO = new ColumnDTO();
    columnDTO.setName(column.getName());

    if (column.getTasks() != null) {
      List<TaskDTO> tasksDTO = column.getTasks().stream()
          .map(this::toTaskDTO)
          .collect(Collectors.toList());
      columnDTO.setTasksDTO(tasksDTO);
    }
    return columnDTO;
  }

  public Column toColumn(ColumnDTO columnDTO) {
    Column column = new Column();
    column.setName(columnDTO.getName());

    if (columnDTO.getTasksDTO() != null) {
      List<Task> tasks = columnDTO.getTasksDTO().stream()
          .map(this::toTask)
          .collect(Collectors.toList());
      tasks.forEach(n -> n.setColumn(column));
      column.setTasks(tasks);
    }
    return column;
  }

  public List<ColumnDTO> toColumnListDTO(List<Column> columns) {
    List<ColumnDTO> columnDTOList = new ArrayList<>();
    for (Column column : columns) {
      ColumnDTO columnDTO = toColumnDTO(column);
      columnDTOList.add(columnDTO);
    }
    return columnDTOList;
  }

  public TaskDTO toTaskDTO(Task task) {
    TaskDTO taskDTO = new TaskDTO();
    taskDTO.setId(task.getId());
    taskDTO.setName(task.getName());
    taskDTO.setTaskIndex(task.getTaskIndex());
    taskDTO.setDescription(task.getDescription());
    return taskDTO;
  }

  public Task toTask(TaskDTO taskDTO) {
    Task task = new Task();
    task.setName(taskDTO.getName());
    task.setDescription(taskDTO.getDescription());
    task.setTaskIndex(taskDTO.getTaskIndex());
    return task;
  }

  public List<TaskDTO> toTaskListDTO(List<Task> tasks) {
    List<TaskDTO> taskDTOs = new ArrayList<>();
    for (Task task : tasks) {
      TaskDTO taskDTO = toTaskDTO(task);
      taskDTOs.add(taskDTO);
    }
    return taskDTOs;
  }
}
