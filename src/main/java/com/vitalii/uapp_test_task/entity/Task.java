package com.vitalii.uapp_test_task.entity;

import com.vitalii.uapp_test_task.command.create.TaskCreateCommand;
import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "task")
@NoArgsConstructor
public class Task extends Domain {

  private String name;
  private String description;
  private int taskIndex;
  private LocalDateTime createDate = LocalDateTime.now();

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "column_id")
  private Column column;

  public Task(TaskCreateCommand command) {
    this.name = command.getName();
    this.description = command.getDescription();
  }
}
