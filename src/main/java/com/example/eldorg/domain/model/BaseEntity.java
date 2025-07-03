package com.example.eldorg.domain.model;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.sql.Timestamp;

@MappedSuperclass
public class BaseEntity {
  @Id private Long id;

  private Timestamp createdAt;
  private Timestamp updatedAt;
}
