package com.example.eldorg.domain.model;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "scripts")
public class Script {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  @Column(length = 1000)
  private String description;

  @Column(nullable = false)
  private String fileUrl;

  @Column(nullable = false)
  private Boolean downloadable;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ScriptStatus status;

  private String rejectionReason;

  @Column(nullable = false, updatable = false)
  private Timestamp createdAt;

  @Column(nullable = false)
  private Timestamp updatedAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;
  // Getters and setters omitted for brevity
}
