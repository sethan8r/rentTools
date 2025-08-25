package dev.sethan8r.renttools.mapper;

import dev.sethan8r.renttools.dto.OrderCreatedDTO;
import dev.sethan8r.renttools.dto.OrderResponseDTO;
import dev.sethan8r.renttools.model.Order;
import dev.sethan8r.renttools.model.Tool;
import dev.sethan8r.renttools.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ToolMapper.class, UserMapper.class})
public interface OrderMapper {

    @Mapping(target = "tool", source = "tool")
    @Mapping(target = "user", source = "user")
    Order toOrder(OrderCreatedDTO orderCreatedDTO, Tool tool, User user);

    @Mapping(target = "toolResponseDTO", source = "tool")
    @Mapping(target = "userResponseDTO", source = "user")
    OrderResponseDTO toOrderResponseDTO(Order order);
}
