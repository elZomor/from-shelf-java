package com.example.eldorg.infrastructure.security;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.example.eldorg.domain.model.User;
import com.example.eldorg.domain.model.UserRole;
import com.example.eldorg.domain.model.UserStatus;
import com.example.eldorg.domain.repo.UserRepo;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

class CustomUserDetailsServiceTest {
  @Mock private UserRepo userRepo;
  @InjectMocks private CustomUserDetailsService service;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void loadUserByUsername_returnsUserDetailsForAdmin() {
    // Arrange
    var user =
        new User("admin", "email", "password", UserRole.ADMIN.name(), UserStatus.ACTIVE.name());
    when(userRepo.findByUsername("admin")).thenReturn(Optional.of(user));

    // Act
    var userDetails = service.loadUserByUsername("admin");

    // Assert
    assertNotNull(userDetails);
    assertEquals("admin", userDetails.getUsername());
    assertEquals("password", userDetails.getPassword());
    assertTrue(userDetails.isEnabled());
  }

  @Test
  void loadUserByUsername_throwsForUnknownUser() {
    // Act & Assert
    assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("unknown"));
  }
}
