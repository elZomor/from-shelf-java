package com.example.eldorg.domain.service;

import static com.example.eldorg.infrastructure.common.RestResponse.resourceNotFoundExceptionResponse;

import com.example.eldorg.domain.model.Script;
import com.example.eldorg.domain.model.ScriptStatus;
import com.example.eldorg.domain.repo.ScriptRepo;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ScriptService {
  private final ScriptRepo scriptRepo;

  public ScriptService(ScriptRepo scriptRepo) {
    this.scriptRepo = scriptRepo;
  }

  public void createScript(String scriptContent) {
    // Logic to create a script
  }

  public List<Script> getScriptsByStatus(ScriptStatus status) {
    return scriptRepo.findByStatus(status.name());
  }

  public Script getScriptByIdAndStatus(Long id, ScriptStatus status) {
    return scriptRepo
        .findByIdAndStatus(id, status.name())
        .orElseThrow(resourceNotFoundExceptionResponse("Script does not exist"));
  }
}
