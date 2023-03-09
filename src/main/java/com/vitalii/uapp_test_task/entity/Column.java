package com.vitalii.uapp_test_task.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Getter
@Setter
@Entity
@Table(name = "column_table")
public class Column extends Domain {

  @NotBlank(message = "Name is mandatory")
  @Size(min = 1, max = 20)
  private String name;
  private int columnIndex;

  @ElementCollection
  @LazyCollection(LazyCollectionOption.FALSE)
  @CollectionTable(
      name = "task",
      joinColumns = @JoinColumn(name = "column_id")
  )
  @javax.persistence.Column(name = "id")
  private Set<UUID> taskIds = new HashSet<>();

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Column column = (Column) o;
    return getId() != null && Objects.equals(getId(), column.getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
