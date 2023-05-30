package com.vitalii.uapp_test_task.dto;

import com.vitalii.uapp_test_task.entity.Column;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ColumnDto {

  private Long id;
  private String name;
  private int columnIndex;
  private List<TaskDto> tasks;

  public ColumnDto(Column column) {
    this.id = column.getId();
    this.name = column.getName();
    this.tasks = column.getTasks().stream()
        .map(TaskDto::new)
        .collect(Collectors.toList());
  }
}
