package com.example.eldorg.domain.service;

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

  public List<Script> getScriptsWithStatus(ScriptStatus status) {
    return scriptRepo.findByStatus(status);
  }
}
