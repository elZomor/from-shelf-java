package com.example.eldorg.domain.repo;

import com.example.eldorg.domain.model.RefreshToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Long> {
  Optional<RefreshToken> findOneByTokenAndUserId(String token, Long userId);
}
