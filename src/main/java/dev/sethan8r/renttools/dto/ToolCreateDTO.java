package dev.sethan8r.renttools.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ToolCreateDTO(
        @NotBlank String name,
        @NotBlank String type,
        @NotNull @Positive Long price,
        @NotNull Long pictureId
) { }
