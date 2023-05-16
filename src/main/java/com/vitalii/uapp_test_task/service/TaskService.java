package com.vitalii.uapp_test_task.service;

import com.vitalii.uapp_test_task.dto.TaskDTO;
import com.vitalii.uapp_test_task.entity.Column;
import com.vitalii.uapp_test_task.entity.Task;
import com.vitalii.uapp_test_task.repository.ColumnRepository;
import com.vitalii.uapp_test_task.repository.TaskRepository;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

  private static final Logger LOG = Logger.getLogger(TaskService.class.getName());

  private final TaskRepository taskRepository;
  private final ColumnRepository columnRepository;
  private final EntityMapper entityMapper;

  public TaskService(TaskRepository taskRepository, ColumnRepository columnRepository,
      EntityMapper entityMapper) {
    this.taskRepository = taskRepository;
    this.columnRepository = columnRepository;
    this.entityMapper = entityMapper;
  }

  public TaskDTO create(TaskDTO taskDTO) {
    LOG.info("Saving task: " + taskDTO.toString());

    Task task = entityMapper.toTask(taskDTO);
    Task savedTask = taskRepository.save(task);

    LOG.info("Saved task with id: " + savedTask.getId());

    return entityMapper.toTaskDTO(savedTask);
  }

  public TaskDTO getById(Long id) {
    LOG.info("Fetching task by id: " + id);

    Task task = taskRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));

    LOG.info("Retrieved  task with id: " + id);

    return entityMapper.toTaskDTO(task);
  }

  public List<TaskDTO> getAll() {
    LOG.info("Fetching all tasks");

    List<Task> tasks = taskRepository.findAll().stream()
        .sorted(comparator)
        .collect(Collectors.toList());

    LOG.info("Retrieved " + tasks.size() + " tasks");
    return entityMapper.toTaskListDTO(tasks);
  }

  public TaskDTO update(Long id, TaskDTO taskDTO, Long columnId) {
    LOG.info("Updating task with id: " + id);

    Task task = taskRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));
    task.setName(taskDTO.getName());
    task.setDescription(taskDTO.getDescription());
    task.setTaskIndex(taskDTO.getTaskIndex());

    Column column = columnRepository.findById(columnId)
        .orElseThrow(() -> new EntityNotFoundException("Column not found with id: " + id));
    task.setColumn(column);
    Task savedTask = taskRepository.save(task);

    LOG.info("Task updated with id: " + savedTask.getId());

    return entityMapper.toTaskDTO(savedTask);
  }

  public void delete(Long id) {
    Task task = taskRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));

    LOG.info("Deleting task with id: " + id);

    taskRepository.delete(task);
  }

//
//  public Task moveColumn(UUID id, UUID newForeignKey) {
//    Task task = findById(id);
//    task.setColumnId(newForeignKey);
//    return save(task);
//  }
//
//  public Map<String, Object> changeTaskIndex(UUID firstTaskId, UUID secondTaskId) {
//    Task firstTask = findById(firstTaskId);
//    Task secondTask = findById(secondTaskId);
//
//    int tmp = firstTask.getTaskIndex();
//    firstTask.setTaskIndex(secondTask.getTaskIndex());
//    secondTask.setTaskIndex(tmp);
//
//    save(firstTask);
//    save(secondTask);
//
//    Map<String, Object> map = new HashMap<>();
//    map.put("firstTask", firstTask);
//    map.put("secondTask", secondTask);
//    return map;
//  }


  private final Comparator<Task> comparator = ((o1, o2) -> {
    if (o1.getTaskIndex() == o2.getTaskIndex()) {
      return 0;
    }

    return (o1.getTaskIndex() > o2.getTaskIndex()) ? 1 : -1;
  });
}
