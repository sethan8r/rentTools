package dev.sethan8r.renttools.exception;

public record ApiError(
        String code,
        String message
) { }
