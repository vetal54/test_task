package com.vitalii.uapp_test_task.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TaskDTO {

  private Long id;

  @NotBlank(message = "Name is mandatory")
  @Size(min = 1, max = 20)
  private String name;

  @NotBlank(message = "Description is mandatory")
  @Size(min = 1, max = 100)
  private String description;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createDate = LocalDateTime.now();
  private int taskIndex;

}
