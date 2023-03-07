package com.vitalii.uapp_test_task.service;

import com.vitalii.uapp_test_task.entity.Domain;
import com.vitalii.uapp_test_task.exception.ResourceNotFoundException;
import com.vitalii.uapp_test_task.repository.AbstractRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;

public abstract class AbstractService<E extends Domain, R extends AbstractRepository<E>> implements
    CommonService<E> {

  protected final R repository;
  @Value("${exception.notFound}")
  private String resourceNotFoundText;

  AbstractService(R repository) {
    this.repository = repository;
  }

  @Override
  public E save(E e) {
    return repository.save(e);
  }

  @Override
  public E update(E e) {
    return repository.save(e);
  }

  @Override
  public E findById(UUID id) {
    return repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(resourceNotFoundText + id));
  }

  @Override
  public void delete(UUID id) {
    repository.deleteById(id);
  }
}
