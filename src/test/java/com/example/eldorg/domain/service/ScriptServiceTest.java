package com.example.eldorg.domain.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.eldorg.domain.model.Script;
import com.example.eldorg.domain.model.ScriptStatus;
import com.example.eldorg.domain.repo.ScriptRepo;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ScriptServiceTest {
  @Mock private ScriptRepo scriptRepo;
  @InjectMocks private ScriptService scriptService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getScriptsByStatus_returnsList() {
    // Arrange
    var script = new Script();
    when(scriptRepo.findByStatus("APPROVED")).thenReturn(Collections.singletonList(script));

    // Act
    var result = scriptService.getScriptsByStatus(ScriptStatus.APPROVED);

    // Assert
    assertEquals(1, result.size());
    assertEquals(script, result.getFirst());
  }

  @Test
  void getScriptByIdAndStatus_returnsScript() {
    // Arrange
    var script = new Script();
    when(scriptRepo.findByIdAndStatus(1L, "APPROVED")).thenReturn(Optional.of(script));

    // Act
    var result = scriptService.getScriptByIdAndStatus(1L, ScriptStatus.APPROVED);

    // Assert
    assertEquals(script, result);
  }

  @Test
  void getScriptByIdAndStatus_throwsIfNotFound() {
    // Arrange
    when(scriptRepo.findByIdAndStatus(1L, "APPROVED")).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(
        RuntimeException.class,
        () -> scriptService.getScriptByIdAndStatus(1L, ScriptStatus.APPROVED));
  }
}
