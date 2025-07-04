package com.example.eldorg.infrastructure.security;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.eldorg.domain.model.RefreshToken;
import com.example.eldorg.domain.model.User;
import com.example.eldorg.domain.repo.RefreshTokenRepo;
import java.util.Date;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

class JwtServiceTest {
  @Mock private RefreshTokenRepo tokenRepo;
  @InjectMocks private JwtService jwtService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void generateToken_and_extractUsername() {
    // Arrange
    var userDetails = mock(UserDetails.class);
    when(userDetails.getUsername()).thenReturn("user1");

    // Act
    var token = jwtService.generateToken(userDetails);

    // Assert
    assertNotNull(token);
    var username = jwtService.extractUsername(token);
    assertEquals("user1", username);
  }

  @Test
  void generateRefreshToken_savesToken() {
    // Arrange
    var mockedUser = mock(User.class);
    when(mockedUser.getId()).thenReturn(1L);
    var userDetails = mock(CustomUserDetails.class);
    when(userDetails.getUsername()).thenReturn("user1");
    when(userDetails.getUser()).thenReturn(mockedUser);

    // Act
    String refreshToken = jwtService.generateRefreshToken(userDetails);

    // Assert
    assertNotNull(refreshToken);
    var captor = ArgumentCaptor.forClass(RefreshToken.class);
    verify(tokenRepo).save(captor.capture());
    var savedToken = captor.getValue();
    assertEquals(refreshToken, savedToken.getToken());
    assertFalse(savedToken.getRevoked());
    assertNotNull(savedToken.getExpiresAt());
    assertEquals(mockedUser.getId(), savedToken.getUserId());
  }

  @Test
  void isTokenValid_returnsTrueForValidToken() {
    // Arrange
    UserDetails userDetails = mock(UserDetails.class);
    when(userDetails.getUsername()).thenReturn("user1");

    // Act
    String token = jwtService.generateToken(userDetails);

    // Assert
    assertTrue(jwtService.isTokenValid(token, "user1"));
  }

  @Test
  void isTokenValid_returnsFalseForInvalidToken() {
    // Act & Assert
    assertFalse(jwtService.isTokenValid("invalid.token", "user1"));
  }

  @Test
  void isRefreshTokenRevoked_returnsTrueIfNotRevoked() {
    // Arrange
    var refreshToken = new RefreshToken("token", false, new Date(), 1L);
    when(tokenRepo.findOneByTokenAndUserId("token", 1L)).thenReturn(Optional.of(refreshToken));

    // Act & Assert
    assertTrue(jwtService.isRefreshTokenRevoked("token", 1L));
  }

  @Test
  void isRefreshTokenRevoked_returnsFalseIfNotFound() {
    // Arrange
    when(tokenRepo.findOneByTokenAndUserId("token", 1L)).thenReturn(Optional.empty());

    // Act & Assert
    assertFalse(jwtService.isRefreshTokenRevoked("token", 1L));
  }

  @Test
  void revokeRefreshToken_setsRevokedTrue() {
    // Arrange
    var refreshToken = new RefreshToken("token", false, new Date(), 1L);
    when(tokenRepo.findOneByTokenAndUserId("token", 1L)).thenReturn(Optional.of(refreshToken));

    // Act
    jwtService.revokeRefreshToken("token", 1L);

    // Assert
    assertTrue(refreshToken.getRevoked());
    verify(tokenRepo).save(refreshToken);
  }
}
