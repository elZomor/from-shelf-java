package com.example.eldorg.infrastructure.common;

import com.example.eldorg.infrastructure.common.exceptions.BadRequestException;
import com.example.eldorg.infrastructure.common.exceptions.NotFoundException;
import com.example.eldorg.infrastructure.common.exceptions.UnauthorizedException;
import java.util.function.Supplier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public record RestResponse(int status, String message, Object data) {

  public static <T> ResponseEntity<RestResponse> successfulResponse(T data) {
    return ResponseEntity.ok(new RestResponse(200, "OK", data));
  }

  public static Supplier<NotFoundException> resourceNotFoundExceptionResponse(String message) {
    return () -> new NotFoundException(message);
  }

  public static Supplier<UsernameNotFoundException> userNotFoundExceptionResponse(String username) {
    return () -> new UsernameNotFoundException("User not found: " + username);
  }

  public static Supplier<BadRequestException> badRequestExceptionResponse(String message) {
    return () -> new BadRequestException(message);
  }

  public static Supplier<UnauthorizedException> unauthorizedExceptionResponse(String message) {
    return () -> new UnauthorizedException(message);
  }
}
