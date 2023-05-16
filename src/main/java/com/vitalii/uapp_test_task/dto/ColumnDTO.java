package com.vitalii.uapp_test_task.dto;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ColumnDTO {

  @NotBlank(message = "Name is mandatory")
  @Size(min = 1, max = 20)
  private String name;
  private static int columnIndex = 2048;
  private List<TaskDTO> tasksDTO;
}
