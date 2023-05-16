package com.vitalii.uapp_test_task.controller;

import com.vitalii.uapp_test_task.dto.ColumnDTO;
import com.vitalii.uapp_test_task.entity.Column;
import com.vitalii.uapp_test_task.service.ColumnService;
import com.vitalii.uapp_test_task.service.EntityMapper;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/column")
public class ColumnController {

  private final ColumnService columnService;
  private final EntityMapper entityMapper;

  public ColumnController(ColumnService columnService, EntityMapper entityMapper) {
    this.columnService = columnService;
    this.entityMapper = entityMapper;
  }

  @PostMapping
  public ResponseEntity<ColumnDTO> create(@RequestBody ColumnDTO columnDTO) {
    ColumnDTO createdColumnDTO = columnService.save(columnDTO);
    return new ResponseEntity<>(createdColumnDTO, HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ColumnDTO> getById(@PathVariable Long id) {
    ColumnDTO columnDTO = columnService.getById(id);
    return ResponseEntity.ok(columnDTO);
  }

  @GetMapping
  public ResponseEntity<List<ColumnDTO>> getAllColumns() {
    List<ColumnDTO> columns = columnService.getAll();
    return ResponseEntity.ok(columns);
  }

  @PutMapping("/{columnId}")
  public ResponseEntity<ColumnDTO> update(@PathVariable Long columnId,
      @RequestBody ColumnDTO columnDTO) {
    ColumnDTO updateColumnDto = columnService.update(columnId, columnDTO);
    return ResponseEntity.ok(updateColumnDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    columnService.delete(id);
    return ResponseEntity.noContent().build();
  }

//  @GetMapping
//  public List<Column> readAll() {
//    return service.findAll();
//  }
//
//  @PutMapping("/change/{id1}/{id2}")
//  public ResponseEntity<Object> changeIndex(@PathVariable UUID id1, @PathVariable UUID id2) {
//    Map<String, Column> map = service.changeColumnIndex(id1, id2);
//    return ResponseEntity.ok().body(map);
//  }

}
