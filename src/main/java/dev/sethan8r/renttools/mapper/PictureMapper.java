package dev.sethan8r.renttools.mapper;

import dev.sethan8r.renttools.dto.PictureCreateDTO;
import dev.sethan8r.renttools.model.Picture;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PictureMapper {

    @Mapping(target = "id", ignore = true)
    Picture toPicture(PictureCreateDTO pictureCreateDTO);
}
