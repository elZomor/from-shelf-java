package com.example.eldorg.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.eldorg.application.dto.AuthenticationRequest;
import com.example.eldorg.application.dto.AuthenticationResponse;
import com.example.eldorg.application.dto.RegisterRequest;
import com.example.eldorg.domain.model.User;
import com.example.eldorg.domain.model.UserRole;
import com.example.eldorg.domain.model.UserStatus;
import com.example.eldorg.domain.repo.UserRepo;
import com.example.eldorg.infrastructure.security.CustomUserDetails;
import com.example.eldorg.infrastructure.security.CustomUserDetailsService;
import com.example.eldorg.infrastructure.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

class AuthenticationServiceTest {
  @Mock private UserRepo userRepo;
  @Mock private PasswordEncoder passwordEncoder;
  @Mock private JwtService jwtService;
  @Mock private AuthenticationManager authenticationManager;
  @Mock private CustomUserDetailsService userDetailsService;
  @Mock private Authentication authentication;
  @InjectMocks private AuthenticationService authenticationService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void authenticate_success() {
    // Arrange
    var request = new AuthenticationRequest("user", "pass");
    var userDetails = mock(CustomUserDetails.class);
    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
        .thenReturn(authentication);
    when(authentication.getPrincipal()).thenReturn(userDetails);
    when(jwtService.generateToken(userDetails)).thenReturn("access");
    when(jwtService.generateRefreshToken(userDetails)).thenReturn("refresh");

    // Act
    AuthenticationResponse response = authenticationService.authenticate(request);

    // Assert
    assertEquals("access", response.accessToken());
    assertEquals("refresh", response.refreshToken());
  }

  @Test
  void register_success() {
    // Arrange
    var request = new RegisterRequest("user", "email", "pass");
    when(passwordEncoder.encode("pass")).thenReturn("encoded");
    when(jwtService.generateToken(any())).thenReturn("access");
    when(jwtService.generateRefreshToken(any())).thenReturn("refresh");

    // Act
    AuthenticationResponse response = authenticationService.register(request);

    // Assert
    assertEquals("access", response.accessToken());
    assertEquals("refresh", response.refreshToken());
    ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
    verify(userRepo).save(userCaptor.capture());
    var savedUser = userCaptor.getValue();
    assertEquals("user", savedUser.getUsername());
    assertEquals("email", savedUser.getEmail());
    assertEquals("encoded", savedUser.getPassword());
    assertEquals(UserRole.USER.name(), savedUser.getRole());
    assertEquals(UserStatus.PENDING.name(), savedUser.getStatus());
  }

  @Test
  void getNewAccessToken_success() {
    // Arrange
    var refreshToken = "refresh";
    var userDetails = mock(CustomUserDetails.class);
    var user = new User("user", "email", "pass", UserRole.USER.name(), UserStatus.ACTIVE.name());
    when(jwtService.extractUsername(refreshToken)).thenReturn("user");
    when(userDetailsService.loadUserByUsername("user")).thenReturn(userDetails);
    when(userDetails.getUser()).thenReturn(user);
    when(jwtService.isTokenValid(refreshToken, user.getUsername())).thenReturn(true);
    when(jwtService.isRefreshTokenRevoked(refreshToken, user.getId())).thenReturn(true);
    when(jwtService.generateToken(userDetails)).thenReturn("access");

    // Act
    AuthenticationResponse response = authenticationService.getNewAccessToken(refreshToken);

    // Assert
    assertEquals("access", response.accessToken());
    assertEquals(refreshToken, response.refreshToken());
  }

  @Test
  void logout_success() {
    // Arrange
    var token = "refresh";
    var userDetails = mock(CustomUserDetails.class);
    var user = new User("user", "email", "pass", UserRole.USER.name(), UserStatus.ACTIVE.name());
    when(jwtService.extractUsername(token)).thenReturn("user");
    when(userDetailsService.loadUserByUsername("user")).thenReturn(userDetails);
    when(userDetails.getUser()).thenReturn(user);

    // Act
    authenticationService.logout(token);

    // Assert
    verify(jwtService).revokeRefreshToken(token, user.getId());
  }
}
