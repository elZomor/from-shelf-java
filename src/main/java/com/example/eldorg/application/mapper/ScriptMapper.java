package com.example.eldorg.application.mapper;

import com.example.eldorg.application.dto.ScriptDTO;
import com.example.eldorg.domain.model.Script;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScriptMapper {
  ScriptDTO scriptToDto(Script script);

  List<ScriptDTO> scriptListToDtoList(List<Script> scripts);
}
