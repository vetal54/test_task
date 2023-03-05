package com.vitalii.uapp_test_task.entity;

import jakarta.persistence.MappedSuperclass;
import java.util.UUID;
import lombok.Getter;

import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@MappedSuperclass
public class Domain {

  @Id
  UUID id = UUID.randomUUID();
}
