package com.example.eldorg.domain.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User extends BaseEntity {
  private String username;
  private String email;
  private String password;
  private UserRole role;
  private UserStatus status;
}
