package com.example.eldorg.infrastructure.common.exceptions;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends RestException {
  public UnauthorizedException(String message) {
    super(message, HttpStatus.UNAUTHORIZED.value());
  }
}
