package com.vitalii.uapp_test_task.entity;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class Domain {

  @Id
  private UUID id = UUID.randomUUID();
}
