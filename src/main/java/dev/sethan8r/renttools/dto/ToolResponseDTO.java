package dev.sethan8r.renttools.dto;

import dev.sethan8r.renttools.model.Picture;

public record ToolResponseDTO(
        Long id,
        String name,
        String type,
        Long price,
        Picture picture,
        Boolean isAvailable
) { }
