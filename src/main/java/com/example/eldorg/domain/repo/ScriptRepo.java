package com.example.eldorg.domain.repo;

import com.example.eldorg.domain.model.Script;
import com.example.eldorg.domain.model.ScriptStatus;
import com.example.eldorg.domain.model.UserRole;
import com.example.eldorg.domain.model.UserStatus;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ScriptRepo extends JpaRepository<Script, Long> {
  List<Script> findByUserId(Long userId);

  List<Script> findByStatus(ScriptStatus status);

  Optional<Script> findByUuid(UUID uuid);

  List<Script> findByUserIdAndStatus(Long userId, ScriptStatus status);

  @Query("SELECT s FROM Script s JOIN s.user u WHERE u.role = :role AND s.status = :status")
  List<Script> findByUserRoleAndStatus(
      @Param("role") UserRole role, @Param("status") ScriptStatus status);

  @Query(
      "SELECT s FROM Script s JOIN s.user u WHERE u.status = :status AND s.status = :scriptStatus")
  List<Script> findByUserStatusAndScriptStatus(
      @Param("status") UserStatus status, @Param("scriptStatus") ScriptStatus scriptStatus);
}
