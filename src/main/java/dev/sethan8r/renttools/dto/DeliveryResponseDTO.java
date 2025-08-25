package dev.sethan8r.renttools.dto;

import dev.sethan8r.renttools.model.DeliveryStatus;

public record DeliveryResponseDTO(
        Long id,
        OrderResponseDTO orderResponseDTO,
        String phone,
        DeliveryStatus deliveryStatus
) { }
