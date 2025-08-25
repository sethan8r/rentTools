package dev.sethan8r.renttools.dto;

public record UserResponseDTO(
        Long id,
        String username,
        String firstName,
        String lastName,
        String phone,
        String email
) { }
