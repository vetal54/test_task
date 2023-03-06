package com.vitalii.uapp_test_task.controller;

import com.vitalii.uapp_test_task.entity.Column;
import com.vitalii.uapp_test_task.service.ColumnService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
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
}
