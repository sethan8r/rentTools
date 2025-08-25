package dev.sethan8r.renttools.dto;

import jakarta.validation.constraints.NotBlank;

public record PictureCreateDTO(
        @NotBlank String url
) { }
