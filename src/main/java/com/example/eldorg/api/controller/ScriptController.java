package com.example.eldorg.api.controller;

import com.example.eldorg.application.dto.ScriptDTO;
import com.example.eldorg.application.mapper.ScriptMapper;
import com.example.eldorg.domain.model.Script;
import com.example.eldorg.domain.model.ScriptStatus;
import com.example.eldorg.domain.service.ScriptService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/scripts")
public class ScriptController {
  private final ScriptService scriptService;
  private final ScriptMapper scriptMapper;

  public ScriptController(ScriptService scriptService, ScriptMapper scriptMapper) {
    this.scriptService = scriptService;
    this.scriptMapper = scriptMapper;
  }

  @GetMapping
  public List<Script> getAllScripts() {
    List<ScriptDTO> scriptDTOS =
        scriptMapper.toDtoList(scriptService.getScriptsWithStatus(ScriptStatus.APPROVED));
    return scriptService.getScriptsWithStatus(ScriptStatus.APPROVED);
  }
}
