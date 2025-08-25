package dev.sethan8r.renttools.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserCreateDTO(
        @NotBlank String username,
        @NotBlank String firstName,
        @NotBlank String lastName,
        @Pattern(regexp = "^\\+7\\(\\d{3}\\)\\d{3}-\\d{2}-\\d{2}$",
                message = "Телефон должен быть в формате +7(XXX)XXX-XX-XX")
        @NotBlank String phone,
        @Email String email,
        @NotBlank String password
) { }
