package dev.sethan8r.renttools.dto;

import jakarta.validation.constraints.NotNull;

public record PasswordChangeRequest(
        @NotNull String password
) { }
