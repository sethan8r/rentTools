package dev.sethan8r.renttools.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record OrderCreatedDTO(
        @NotNull Long toolId,
        @NotNull Long userId,
        @NotNull LocalDate startDate,
        @NotNull LocalDate endDate,
        @NotBlank String address
) { }
