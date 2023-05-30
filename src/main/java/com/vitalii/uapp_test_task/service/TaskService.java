package com.vitalii.uapp_test_task.service;

import com.vitalii.uapp_test_task.dto.TaskDto;
import com.vitalii.uapp_test_task.entity.Task;
import com.vitalii.uapp_test_task.command.create.TaskCreateCommand;
import com.vitalii.uapp_test_task.exception.ResourceNotFoundException;
import com.vitalii.uapp_test_task.repository.TaskRepository;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

  private static final Logger LOG = Logger.getLogger(TaskService.class.getName());

  private final TaskRepository taskRepository;

  public TaskDto create(TaskCreateCommand command) {
    LOG.info("Saving task: " + command.toString());

    Task task = new Task(command);
    Task savedTask = taskRepository.save(task);

    LOG.info("Saved task with id: " + savedTask.getId());
    return new TaskDto(task);
  }

  public Task getById(Long id) {
    LOG.info("Fetching task by id: " + id);

    Task task = taskRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

    LOG.info("Retrieved  task with id: " + id);
    return task;
  }

  public List<Task> getAllByColumnId(Long columnId) {
    LOG.info("Fetching all tasks");

    List<Task> tasks = taskRepository.findAllByColumnId(columnId)
        .stream()
        .sorted(Comparator.comparing(Task::getTaskIndex))
        .collect(Collectors.toList());

    LOG.info("Retrieved " + tasks.size() + " tasks");
    return tasks;
  }

  public TaskDto update(Long id, TaskCreateCommand command) {
    LOG.info("Updating task with id: " + id);

    Task task = getById(id);
    task.setName(command.getName());
    task.setDescription(command.getDescription());
    Task updatedTask = taskRepository.save(task);

    LOG.info("Task updated with id: " + updatedTask.getId());
    return new TaskDto(updatedTask);
  }

  public void delete(Long id) {
    Task task = getById(id);

    LOG.info("Deleting task with id: " + id);

    taskRepository.delete(task);
  }

  public TaskDto changeOrder(Long id, int position) {
    LOG.info("Performing position change with id: " + id + ", position: " + position);

    Task task = getById(id);
    List<Task> tasks = getAllByColumnId(task.getColumn().getId());

    if (tasks.size() == 1 || tasks.indexOf(task) == position) {
      LOG.info("Returning task without changing order");
      return new TaskDto(task);
    }

    if (position == 0) {
      LOG.info("Calculating index for start position");
      task.setTaskIndex(calculateIndexForStart(position, tasks));
    } else if (position == tasks.size()) {
      LOG.info("Calculating index for end position");
      task.setTaskIndex(calculateIndexForEnd(position, tasks));
    } else {
      LOG.info("Calculating index for middle position");
      task.setTaskIndex(calculateIndexForMiddle(position, tasks));
    }
    LOG.info("Saving updated task");
    return new TaskDto(taskRepository.save(task));
  }

  private int calculateIndexForStart(int position, List<Task> tasks) {
    LOG.info("Executing calculateIndexForStart with position: " + position);

    int newStartIndex = tasks.get(position).getTaskIndex() / 2;
    if (newStartIndex % 2 != 0) {
      LOG.info("Recalculating task indexes");
      tasks = recalculateTaskIndexes(tasks);
      newStartIndex = tasks.get(position).getTaskIndex() / 2;
    }
    return newStartIndex;
  }

  private int calculateIndexForEnd(int position, List<Task> tasks) {
    LOG.info("Executing calculateIndexForEnd with position: " + position);

    long nextIndex = (long) Math.pow(2, tasks.size() + 11D);
    int endIndex = tasks.get(position - 1).getTaskIndex();
    int newEndIndex = (int) ((endIndex + nextIndex) / 2);
    if (newEndIndex % 2 != 0) {
      LOG.info("Recalculating task indexes");
      tasks = recalculateTaskIndexes(tasks);
      newEndIndex = (int) ((tasks.get(position - 1).getTaskIndex() + nextIndex) / 2);
    }
    return newEndIndex;
  }

  private int calculateIndexForMiddle(int position, List<Task> tasks) {
    LOG.info("Executing calculateIndexForMiddle with position: " + position);

    int previousIndex = tasks.get(position - 1).getTaskIndex();
    int nextIndex = tasks.get(position).getTaskIndex();
    int newIndex = (previousIndex + nextIndex) / 2;
    if (newIndex % 2 != 0) {
      LOG.info("Recalculating task indexes");
      tasks = recalculateTaskIndexes(tasks);
      newIndex =
          (tasks.get(position - 1).getTaskIndex() + tasks.get(position).getTaskIndex()) / 2;
    }
    return newIndex;
  }

  private List<Task> recalculateTaskIndexes(List<Task> tasks) {
    LOG.info("Executing recalculateTaskIndexes");

    int power = 11;
    for (Task task : tasks) {
      task.setTaskIndex((int) Math.pow(2, power));
      power++;
    }
    return taskRepository.saveAll(tasks);
  }
}
