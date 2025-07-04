package com.example.eldorg.domain.model;

import jakarta.persistence.*;
import java.util.Date;
import lombok.*;

@Entity
@Table(name = "refresh_tokens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken extends BaseEntity {
  private String token;
  private Boolean revoked;
  private Date expiresAt;
  private Long userId;
}
