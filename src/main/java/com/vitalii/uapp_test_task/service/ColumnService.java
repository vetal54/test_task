package com.vitalii.uapp_test_task.service;

import com.vitalii.uapp_test_task.entity.Column;
import com.vitalii.uapp_test_task.repository.ColumnRepository;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ColumnService extends AbstractService<Column, ColumnRepository> {

  public ColumnService(ColumnRepository repository) {
    super(repository);
  }

  public List<Column> findAll() {
    return repository.findAll().stream()
        .sorted(comparator)
        .collect(Collectors.toList());
  }

  public Map<String, Object> changeColumnIndex(UUID firstColumnId, UUID secondColumnId) {
    Column firstColumn = findById(firstColumnId);
    Column secondColumn = findById(secondColumnId);

    int tmp = firstColumn.getColumnIndex();
    firstColumn.setColumnIndex(secondColumn.getColumnIndex());
    secondColumn.setColumnIndex(tmp);

    save(firstColumn);
    save(secondColumn);

    Map<String, Object> map = new HashMap<>();
    map.put("firstColumn", firstColumn);
    map.put("secondColumn", secondColumn);
    return map;
  }

  private final Comparator<Column> comparator = ((o1, o2) -> {
    if (o1.getColumnIndex() == o2.getColumnIndex()) {
      return 0;
    }

    return (o1.getColumnIndex() > o2.getColumnIndex()) ? 1 : -1;
  });
}
