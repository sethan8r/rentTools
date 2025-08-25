package dev.sethan8r.renttools.mapper;

import dev.sethan8r.renttools.dto.PictureCreateDTO;
import dev.sethan8r.renttools.model.Picture;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PictureMapper {

    Picture toPicture(PictureCreateDTO pictureCreateDTO);
}
