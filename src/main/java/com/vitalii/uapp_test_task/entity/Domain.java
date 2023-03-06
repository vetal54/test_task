package com.vitalii.uapp_test_task.entity;

import java.util.UUID;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class Domain {

  @Id
  private UUID id = UUID.randomUUID();
}
