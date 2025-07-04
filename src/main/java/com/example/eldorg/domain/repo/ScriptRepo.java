package com.example.eldorg.domain.repo;

import com.example.eldorg.domain.model.Script;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScriptRepo extends JpaRepository<Script, Long> {
  List<Script> findByStatus(String status);

  Optional<Script> findByIdAndStatus(Long id, String status);
}
