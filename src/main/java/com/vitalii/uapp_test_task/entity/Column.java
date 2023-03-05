package com.vitalii.uapp_test_task.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class Column extends Domain {

  private String name;
}
