package com.vitalii.uapp_test_task.service;

import com.vitalii.uapp_test_task.entity.Task;
import com.vitalii.uapp_test_task.repository.TaskRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class TaskService extends AbstractService<Task, TaskRepository> {

  public TaskService(TaskRepository repository) {
    super(repository);
  }

  public List<Task> findAllByForeignId(UUID id) {
    return repository.findAllByColumnId(id);
  }
}
