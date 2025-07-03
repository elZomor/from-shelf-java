package com.example.eldorg.api.common;

public record RestResponse<T>(String status, String message, T data) {}
