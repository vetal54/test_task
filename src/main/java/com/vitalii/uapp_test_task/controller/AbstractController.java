package com.vitalii.uapp_test_task.controller;

import com.vitalii.uapp_test_task.entity.Domain;
import com.vitalii.uapp_test_task.service.CommonService;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class AbstractController<E extends Domain, S extends CommonService<E>> implements
    CommonController<E> {

  protected final S service;

  AbstractController(S service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<E> create(@RequestBody E e) {
    E entityResponse = service.save(e);
    return new ResponseEntity<>(entityResponse, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<E> update(@PathVariable UUID id, @RequestBody E resource) {
    service.findById(id);
    resource.setId(id);
    E response = service.update(resource);

    return ResponseEntity.ok().body(response);
  }

  @DeleteMapping
  public HttpStatus delete(UUID id) {
    service.delete(id);
    return HttpStatus.OK;
  }

  @GetMapping("/{id}")
  public ResponseEntity<E> readById(@PathVariable UUID id) {
    E response = service.findById(id);
    return ResponseEntity.ok().body(response);
  }
}
