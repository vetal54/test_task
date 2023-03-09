package com.vitalii.uapp_test_task.service;

import com.vitalii.uapp_test_task.entity.Task;
import com.vitalii.uapp_test_task.repository.TaskRepository;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class TaskService extends AbstractService<Task, TaskRepository> {

  public TaskService(TaskRepository repository) {
    super(repository);
  }

  public List<Task> findAllById(List<UUID> ids) {
    return repository.findAllById(ids)
        .stream()
        .sorted(comparator)
        .collect(Collectors.toList());
  }

  public Task moveColumn(UUID id, UUID newForeignKey) {
    Task task = findById(id);
    task.setColumnId(newForeignKey);
    return save(task);
  }

  public Map<String, Object> changeTaskIndex(UUID firstTaskId, UUID secondTaskId) {
    Task firstTask = findById(firstTaskId);
    Task secondTask = findById(secondTaskId);

    int tmp = firstTask.getTaskIndex();
    firstTask.setTaskIndex(secondTask.getTaskIndex());
    secondTask.setTaskIndex(tmp);

    save(firstTask);
    save(secondTask);

    Map<String, Object> map = new HashMap<>();
    map.put("firstTask", firstTask);
    map.put("secondTask", secondTask);
    return map;
  }

  private final Comparator<Task> comparator = ((o1, o2) -> {
    if (o1.getTaskIndex() == o2.getTaskIndex()) {
      return 0;
    }

    return (o1.getTaskIndex() > o2.getTaskIndex()) ? 1 : -1;
  });
}
