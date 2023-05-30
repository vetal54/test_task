package com.vitalii.uapp_test_task.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vitalii.uapp_test_task.entity.Task;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskDto {

  private Long id;
  private String name;
  private String description;
  private int taskIndex;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createDate;

  public TaskDto(Task task) {
    this.id = task.getId();
    this.name = task.getName();
    this.description = task.getDescription();
    this.taskIndex = task.getTaskIndex();
    this.createDate = task.getCreateDate();
  }
}
