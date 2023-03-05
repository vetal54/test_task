package com.vitalii.uapp_test_task.service;

import com.vitalii.uapp_test_task.entity.Column;
import com.vitalii.uapp_test_task.repository.ColumnRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ColumnService extends AbstractService<Column, ColumnRepository> {

  public ColumnService(ColumnRepository repository) {
    super(repository);
  }

  public List<Column> findAll() {
    return repository.findAll();
  }
}
