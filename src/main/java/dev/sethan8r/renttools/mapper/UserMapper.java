package dev.sethan8r.renttools.mapper;

import dev.sethan8r.renttools.dto.UserCreateDTO;
import dev.sethan8r.renttools.dto.UserResponseDTO;
import dev.sethan8r.renttools.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserCreateDTO userCreateDTO);

    UserResponseDTO toUserResponseDTO(User user);
}
