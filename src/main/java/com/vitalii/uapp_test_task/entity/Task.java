package com.vitalii.uapp_test_task.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

@Getter
@Setter
@Entity
@Table(name = "task")
public class Task extends Domain {

  @NotBlank(message = "Name is mandatory")
  @Size(min = 1, max = 20)
  private String name;

  @NotBlank(message = "Description is mandatory")
  @Size(min = 1, max = 100)
  private String description;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createDate = LocalDateTime.now();
  private int taskIndex;

  @Column(name = "column_id")
  private UUID columnId;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Task task = (Task) o;
    return getId() != null && Objects.equals(getId(), task.getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
