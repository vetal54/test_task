package com.vitalii.uapp_test_task.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger LOG = Logger.getLogger(GlobalExceptionHandler.class.getName());

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException ex) {
    String errLogMessage = "MethodArgumentNotValidException occurred: " + ex;
    LOG.log(Level.SEVERE, errLogMessage);
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach(error -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  protected ResponseEntity<Map<String, Object>> handleEntityResourceNotFoundException(
      ResourceNotFoundException ex) {
    String errLogMessage = "ResourceNotFoundException occurred: " + ex;
    LOG.log(Level.SEVERE, errLogMessage);
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("date", LocalDateTime.now());
    body.put("message", ex.getMessage());
    return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ResourceOverflowException.class)
  protected ResponseEntity<Map<String, Object>> handleEntityResourceOverflowException(
      ResourceNotFoundException ex) {
    String errLogMessage = "ResourceOverflowException occurred: " + ex;
    LOG.log(Level.SEVERE, errLogMessage);
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("date", LocalDateTime.now());
    body.put("message", ex.getMessage());
    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }
}
