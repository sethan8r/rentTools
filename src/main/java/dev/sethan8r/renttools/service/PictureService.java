package dev.sethan8r.renttools.service;

import dev.sethan8r.renttools.dto.PictureCreateDTO;
import dev.sethan8r.renttools.exception.NotFoundException;
import dev.sethan8r.renttools.mapper.PictureMapper;
import dev.sethan8r.renttools.model.Picture;
import dev.sethan8r.renttools.repository.PictureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PictureService {

    private final PictureRepository pictureRepository;
    private final PictureMapper pictureMapper;

    public List<Picture> getAllPicture() {
        return pictureRepository.findAll();
    }

    public Picture getPictureById(Long id) {
        return pictureRepository.findById(id).orElseThrow( () ->
                new NotFoundException("Изображение с ID " + id + " не найден"));
    }

    public void createPicture(PictureCreateDTO pictureCreateDTO) {
        pictureRepository.save(pictureMapper.toPicture(pictureCreateDTO));
    }

    public void deletePicture(Long id) {
        if(pictureRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Изображение с ID " + id + " не найден");
        }
        pictureRepository.deleteById(id);
    }
}
