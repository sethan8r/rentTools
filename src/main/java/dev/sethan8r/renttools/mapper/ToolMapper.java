package dev.sethan8r.renttools.mapper;

import dev.sethan8r.renttools.dto.ToolCreateDTO;
import dev.sethan8r.renttools.dto.ToolResponseDTO;
import dev.sethan8r.renttools.model.Tool;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ToolMapper {

    Tool toTool(ToolCreateDTO toolCreateDTO);

    ToolResponseDTO toToolResponseDTO(Tool tool);
}
