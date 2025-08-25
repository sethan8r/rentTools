package dev.sethan8r.renttools.mapper;

import dev.sethan8r.renttools.dto.CourierCreateDTO;
import dev.sethan8r.renttools.dto.CourierResponseDTO;
import dev.sethan8r.renttools.model.Courier;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourierMapper {

    Courier toCourier(CourierCreateDTO courierCreateDTO);

    CourierResponseDTO toCourierResponseDTO(Courier courier);
}
