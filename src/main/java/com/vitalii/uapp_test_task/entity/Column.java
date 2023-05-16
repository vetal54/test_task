package com.vitalii.uapp_test_task.entity;

import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

@Getter
@Setter
@Entity
@Table(name = "column_table")
public class Column extends Domain {

  private String name;
  private int columnIndex;

  @OneToMany(mappedBy = "column", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<Task> tasks;

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
