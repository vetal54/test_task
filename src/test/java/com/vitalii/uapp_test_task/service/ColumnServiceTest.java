package com.vitalii.uapp_test_task.service;

import com.vitalii.uapp_test_task.entity.Column;
import com.vitalii.uapp_test_task.exception.ResourceNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ColumnServiceTest {

  @Autowired
  private ColumnService columnService;

  @Test
  void save() {
    Column column = createColumn();
    Column saveColumn = columnService.save(column);

    Assertions.assertEquals(column, saveColumn);
  }

  @Test
  void update() {
    Column column = columnService.save(createColumn());
    column.setName("new Name");

    Column updateColumn = columnService.update(column);

    Assertions.assertEquals("new Name", updateColumn.getName());
  }

  @Test
  void getById() {
    Column column = columnService.save(createColumn());
    Column columnGetById = columnService.findById(column.getId());

    Assertions.assertEquals(column, columnGetById);
  }

  @Test
  void findByIdFalse() {
    UUID randomId = UUID.randomUUID();

    Assertions.assertThrows(ResourceNotFoundException.class,
        () -> columnService.findById(randomId));
  }

  @Test
  void getAll() {
    Column firstColumn = columnService.save(createColumn());
    Column secondColumn = columnService.save(createColumn());
    List<Column> columns = columnService.findAll();

    Assertions.assertAll(
        () -> Assertions.assertEquals(2, columns.size()),
        () -> Assertions.assertTrue(columns.contains(firstColumn)),
        () -> Assertions.assertTrue(columns.contains(secondColumn))
    );
  }

  @Test
  void deleteById() {
    Column column = columnService.save(createColumn());

    columnService.delete(column.getId());

    Assertions.assertThrows(ResourceNotFoundException.class,
        () -> columnService.findById(column.getId()));
  }

  @Test
  void changeColumnIndex() {
    Column firstColumn = columnService.save(createColumn());
    Column column = new Column();
    column.setName("Test");
    column.setColumnIndex(firstColumn.getColumnIndex() + 1);

    Column secondColumn = columnService.save(column);

    Map<String, Column> map = columnService.changeColumnIndex(firstColumn.getId(),
        secondColumn.getId());

    Assertions.assertAll(
        () -> Assertions.assertEquals(1, map.get("firstColumn").getColumnIndex()),
        () -> Assertions.assertEquals(0, map.get("secondColumn").getColumnIndex())
    );
  }

  private Column createColumn() {
    Column column = new Column();
    column.setName("test");
    column.setColumnIndex(0);
    return column;
  }
}