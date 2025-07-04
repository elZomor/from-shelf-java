package com.example.eldorg.api.controller;

import static com.example.eldorg.infrastructure.common.RestResponse.successfulResponse;

import com.example.eldorg.application.dto.ScriptDTO;
import com.example.eldorg.application.mapper.ScriptMapper;
import com.example.eldorg.domain.model.ScriptStatus;
import com.example.eldorg.domain.service.ScriptService;
import com.example.eldorg.infrastructure.common.RestResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scripts")
public class ScriptController {
  private final ScriptService scriptService;
  private final ScriptMapper scriptMapper;

  public ScriptController(ScriptService scriptService, ScriptMapper scriptMapper) {
    this.scriptService = scriptService;
    this.scriptMapper = scriptMapper;
  }

  @GetMapping
  public ResponseEntity<RestResponse> getAllScripts() {
    List<ScriptDTO> scriptDTOS =
        scriptMapper.scriptListToDtoList(scriptService.getScriptsByStatus(ScriptStatus.APPROVED));
    return successfulResponse(scriptDTOS);
  }

  @GetMapping("/{id}")
  public ResponseEntity<RestResponse> getScript(@PathVariable Long id) {
    ScriptDTO scriptDTOS =
        scriptMapper.scriptToDto(scriptService.getScriptByIdAndStatus(id, ScriptStatus.APPROVED));
    return successfulResponse(scriptDTOS);
  }
}
