package com.vitalii.uapp_test_task.controller;

import com.vitalii.uapp_test_task.entity.Column;
import com.vitalii.uapp_test_task.service.ColumnService;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/column")
public class ColumnController extends AbstractController<Column, ColumnService> {

  public ColumnController(ColumnService columnService) {
    super(columnService);
  }

  @GetMapping
  public List<Column> readAll() {
    return service.findAll();
  }

  @PutMapping("/change/{id1}/{id2}")
  public ResponseEntity<Object> changeIndex(@PathVariable UUID id1, @PathVariable UUID id2) {
    Map<String, Column> map = service.changeColumnIndex(id1, id2);
    return ResponseEntity.ok().body(map);
  }

}
