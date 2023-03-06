package com.vitalii.uapp_test_task.controller;

import com.vitalii.uapp_test_task.entity.Domain;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface CommonController<E extends Domain> {

  ResponseEntity<E> create(E resource);

  ResponseEntity<E> update(UUID id, E resource);

  HttpStatus delete(UUID id);

  ResponseEntity<E> readById(UUID id);
}
