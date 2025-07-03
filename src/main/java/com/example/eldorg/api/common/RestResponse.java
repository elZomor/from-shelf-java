package com.example.eldorg.api.common;

import org.springframework.http.ResponseEntity;

public record RestResponse<T>(String status, String message, T data) {

  public static <T> ResponseEntity<RestResponse<T>> successfulResponse(T data) {
    return ResponseEntity.ok(new RestResponse<>("SUCCESS", "OK", data));
  }

  public static <T> ResponseEntity<RestResponse<T>> error(String message) {
    return ResponseEntity.badRequest().body(new RestResponse<>("ERROR", message, null));
  }
}
