package com.example.eldorg.infrastructure.security;

import com.example.eldorg.domain.model.RefreshToken;
import com.example.eldorg.domain.repo.RefreshTokenRepo;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtService {

  private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // generate secure key
  private final RefreshTokenRepo tokenRepo;

  public JwtService(RefreshTokenRepo tokenRepo) {
    this.tokenRepo = tokenRepo;
  }

  public String generateToken(UserDetails userDetails) {
    return Jwts.builder()
        .setSubject(userDetails.getUsername())
        .setIssuedAt(new Date())
        .setExpiration(Date.from(Instant.now().plus(Duration.ofHours(10))))
        .signWith(key)
        .compact();
  }

  public String generateRefreshToken(CustomUserDetails userDetails) {
    var expirationDate = Date.from(Instant.now().plus(Duration.ofDays(7)));
    var refreshTokenString =
        Jwts.builder()
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date())
            .setExpiration(expirationDate)
            .signWith(key)
            .compact();
    var refreshToken =
        new RefreshToken(refreshTokenString, false, expirationDate, userDetails.getUser().getId());
    tokenRepo.save(refreshToken);
    return refreshTokenString;
  }

  public String extractUsername(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

  public boolean isTokenValid(String token, String username) {
    try {
      var tokenUsername = extractUsername(token);
      return tokenUsername.equals(username);
    } catch (JwtException e) {
      return false;
    }
  }

  public boolean isRefreshTokenRevoked(String token, Long userId) {
    return tokenRepo
        .findOneByTokenAndUserId(token, userId)
        .map(refreshToken -> !refreshToken.getRevoked())
        .orElse(false);
  }

  public void revokeRefreshToken(String token, Long userId) {
    tokenRepo
        .findOneByTokenAndUserId(token, userId)
        .ifPresent(
            refreshToken -> {
              refreshToken.setRevoked(true);
              tokenRepo.save(refreshToken);
            });
  }
}
