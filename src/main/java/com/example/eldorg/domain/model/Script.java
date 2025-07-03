package com.example.eldorg.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "scripts")
@Setter
@Getter
public class Script extends BaseEntity {
  private String title;
  private String description;
  private String fileUrl;
  private Boolean downloadable;
  private String status;
  private String rejectionReason;
  private Long userId;
}
