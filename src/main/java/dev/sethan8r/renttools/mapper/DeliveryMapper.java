package dev.sethan8r.renttools.mapper;

import dev.sethan8r.renttools.dto.DeliveryCreateDTO;
import dev.sethan8r.renttools.dto.DeliveryResponseDTO;
import dev.sethan8r.renttools.model.Courier;
import dev.sethan8r.renttools.model.Delivery;
import dev.sethan8r.renttools.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {OrderMapper.class, CourierMapper.class})
public interface DeliveryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "courier", source = "courier")
    @Mapping(target = "order", source = "order")
    Delivery toDelivery(DeliveryCreateDTO deliveryCreateDTO, Courier courier, Order order);

    @Mapping(target = "orderResponseDTO", source = "order")
    DeliveryResponseDTO toDeliveryResponseDTO(Delivery delivery);
}
