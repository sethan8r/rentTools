package dev.sethan8r.renttools.dto;

import dev.sethan8r.renttools.model.DeliveryStatus;

import java.time.LocalDate;

public record OrderResponseDTO(
        Long id,
        ToolResponseDTO toolResponseDTO,
        UserResponseDTO userResponseDTO,
        Long fullPrice,
        LocalDate startDate,
        LocalDate endDate,
        String address,
        DeliveryStatus status
) { }
