package com.vitalii.uapp_test_task.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "column_table")
@NoArgsConstructor
public class Column extends Domain {

  private String name;
  private int columnIndex;

  @OneToMany(mappedBy = "column", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<Task> tasks;

  public Column(String name) {
    this.name = name;
    tasks = new ArrayList<>();
  }

  public void setTasks(List<Task> tasks) {
    if (tasks != null) {
      tasks.forEach(a -> a.setColumn(this));
    }
    this.tasks = tasks;
  }
}
