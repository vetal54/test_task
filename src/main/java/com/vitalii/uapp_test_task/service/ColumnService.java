package com.vitalii.uapp_test_task.service;

import com.vitalii.uapp_test_task.dto.ColumnDTO;
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
public class ColumnService {

  private static final Logger LOG = Logger.getLogger(ColumnService.class.getName());

  private final ColumnRepository columnRepository;
  private final TaskRepository taskRepository;
  private final EntityMapper entityMapper;

  public ColumnService(ColumnRepository columnRepository, TaskRepository taskRepository,
      EntityMapper entityMapper) {
    this.columnRepository = columnRepository;
    this.taskRepository = taskRepository;
    this.entityMapper = entityMapper;
  }

//  public Map<String, Column> changeColumnIndex(UUID firstColumnId, UUID secondColumnId) {
//    Column firstColumn = findById(firstColumnId);
//    Column secondColumn = findById(secondColumnId);
//
//    int tmp = firstColumn.getColumnIndex();
//    firstColumn.setColumnIndex(secondColumn.getColumnIndex());
//    secondColumn.setColumnIndex(tmp);
//
//    save(firstColumn);
//    save(secondColumn);
//
//    Map<String, Column> map = new HashMap<>();
//    map.put("firstColumn", firstColumn);
//    map.put("secondColumn", secondColumn);
//    return map;
//  }

  public ColumnDTO save(ColumnDTO columnDTO) {
    LOG.info("Saving column: " + columnDTO.toString());

    Column column = entityMapper.toColumn(columnDTO);
    Column savedColumn = columnRepository.save(column);

    LOG.info("Saved column with id: " + savedColumn.getId());

    return entityMapper.toColumnDTO(savedColumn);
  }

  public ColumnDTO getById(Long id) {
    LOG.info("Fetching column by id: " + id);

    Column column = columnRepository.findById(id).orElseThrow(
        () -> new EntityNotFoundException("Column not found with id: " + id));

    LOG.info("Retrieved column with id: " + id);

    return entityMapper.toColumnDTO(column);
  }

  public ColumnDTO update(Long id, ColumnDTO columnDTO) {
    LOG.info("Updating column with id: " + id);

    Column column = columnRepository.findById(id).orElseThrow(
            () -> new EntityNotFoundException("Column not found with id: " + id));
    column.setName(columnDTO.getName());

    List<TaskDTO> taskDTOs = columnDTO.getTasksDTO();
    if (taskDTOs != null) {
      LOG.info("Updating tasks for column with id: " + id);

      List<Task> tasks = taskDTOs.stream()
          .map(taskDTO -> {
            Task task = taskDTO.getId() != null
                ? taskRepository.findById(taskDTO.getId()).orElseThrow()
                : new Task();
            task.setName(taskDTO.getName());
            task.setDescription(taskDTO.getDescription());
            task.setTaskIndex(taskDTO.getTaskIndex());
            task.setColumn(column);
            return task;
          })
          .collect(Collectors.toList());
      column.setTasks(tasks);
    }
    Column savedColumn = columnRepository.save(column);

    LOG.info("Updated column with id: " + savedColumn.getId());

    return entityMapper.toColumnDTO(savedColumn);
  }

  public List<ColumnDTO> getAll() {
    LOG.info("Fetching all columns");
    List<Column> columns = columnRepository.findAll().stream()
        .sorted(comparator)
        .collect(Collectors.toList());

    LOG.info("Retrieved " + columns.size() + " columns");
    return entityMapper.toColumnListDTO(columns);
  }

  public void delete(Long id) {
    Column column = columnRepository.findById(id).orElseThrow(
            () -> new EntityNotFoundException("Column not found with id: " + id));

    LOG.info("Deleting column with id: " + id);

    columnRepository.delete(column);
  }

  private final Comparator<Column> comparator = ((o1, o2) -> {
    if (o1.getColumnIndex() == o2.getColumnIndex()) {
      return 0;
    }

    return (o1.getColumnIndex() > o2.getColumnIndex()) ? 1 : -1;
  });
}
