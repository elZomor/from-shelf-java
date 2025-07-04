package com.example.eldorg.domain.model;

import jakarta.persistence.*;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
public class BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(insertable = false)
  private Timestamp createdAt;

  @Column(insertable = false)
  @Setter
  private Timestamp updatedAt;
}
