package com.example.eldorg.infrastructure.common.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends RestException {
  public NotFoundException(String message) {
    super(message, HttpStatus.NOT_FOUND.value());
  }
}
