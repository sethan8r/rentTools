package dev.sethan8r.renttools.dto;

public record CourierResponseDTO(
        Long id,
        String username,
        Integer delivers,
        String email,
        String firstName,
        String lastName,
        String phone
) { }
