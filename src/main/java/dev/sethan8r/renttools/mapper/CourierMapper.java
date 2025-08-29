package dev.sethan8r.renttools.mapper;

import dev.sethan8r.renttools.dto.CourierCreateDTO;
import dev.sethan8r.renttools.dto.CourierResponseDTO;
import dev.sethan8r.renttools.model.Courier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CourierMapper {

    @Mapping(target = "id", ignore = true)
    Courier toCourier(CourierCreateDTO courierCreateDTO);

    CourierResponseDTO toCourierResponseDTO(Courier courier);
}
