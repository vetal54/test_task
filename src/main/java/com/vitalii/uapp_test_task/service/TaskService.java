package com.vitalii.uapp_test_task.service;

import com.vitalii.uapp_test_task.entity.Task;
import com.vitalii.uapp_test_task.repository.TaskRepository;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class TaskService extends AbstractService<Task, TaskRepository> {

  public TaskService(TaskRepository repository) {
    super(repository);
  }

  public List<Task> findAllById(UUID id) {
    return repository.findAllByColumnId(id)
        .stream()
        .sorted(comparator)
        .collect(Collectors.toList());
  }

  private final Comparator<Task> comparator = ((o1, o2) -> {
    if (o1.getTaskIndex() == o2.getTaskIndex()) {
      return 0;
    }

    return (o1.getTaskIndex() > o2.getTaskIndex()) ? 1 : -1;
  });
}
