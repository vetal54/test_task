package com.vitalii.uapp_test_task.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "column_table")
public class Column extends Domain {

  private String name;
  private int columnIndex;
}
