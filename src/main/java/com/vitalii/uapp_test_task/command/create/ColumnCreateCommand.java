package com.vitalii.uapp_test_task.command.create;

import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ColumnCreateCommand {

  @NotBlank(message = "Name is mandatory")
  @Size(min = 1, max = 20)
  private String name;
  private Set<Long> taskIds;
}
