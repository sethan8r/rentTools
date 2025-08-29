package dev.sethan8r.renttools.dto;

import jakarta.validation.constraints.NotNull;

public record DeliveryCreateDTO(
        @NotNull Long courierId,
        @NotNull Long orderId
) { }
