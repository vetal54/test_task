package com.vitalii.uapp_test_task.service;

import com.vitalii.uapp_test_task.entity.Domain;
import java.util.UUID;

public interface CommonService<E extends Domain> {
  E save(E e);
  E update(E e);
  E findById(UUID id);
  void delete(UUID id);
}
