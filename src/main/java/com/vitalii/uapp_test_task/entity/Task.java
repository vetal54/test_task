package com.vitalii.uapp_test_task.entity;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Task extends Domain {

  private String name;
  private String description;
  private LocalDateTime createDate;

}
