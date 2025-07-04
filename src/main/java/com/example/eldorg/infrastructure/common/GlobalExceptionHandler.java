package com.example.eldorg.infrastructure.common;

import com.example.eldorg.infrastructure.common.exceptions.RestException;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(RestException.class)
  public ResponseEntity<Map<String, Object>> handleRestException(RestException ex) {
    Map<String, Object> error =
        Map.of(
            "error", ex.getMessage(),
            "status", ex.getStatus());
    return ResponseEntity.status(ex.getStatus()).body(error);
  }

  // Optional fallback
  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, Object>> handleOther(Exception ex) {
    log.error(ex.getMessage());
    return ResponseEntity.status(500).body(Map.of("error", "Internal Server Error"));
  }
}
