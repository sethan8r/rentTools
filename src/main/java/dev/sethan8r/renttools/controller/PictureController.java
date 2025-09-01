package dev.sethan8r.renttools.controller;

import dev.sethan8r.renttools.dto.PictureCreateDTO;
import dev.sethan8r.renttools.model.Picture;
import dev.sethan8r.renttools.service.PictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/picture")
@RequiredArgsConstructor
public class PictureController {

    private final PictureService pictureService;

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @GetMapping("/get/all")
    public List<Picture> getAllPicture() {

        return pictureService.getAllPicture();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @GetMapping("/get/{id}")
    public Picture getPictureById(@PathVariable Long id) {

        return pictureService.getPictureById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @PostMapping("/create")
    public ResponseEntity<Void> createPicture(@RequestBody PictureCreateDTO pictureCreateDTO) {
        pictureService.createPicture(pictureCreateDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasRole('SUPERADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePicture(@PathVariable Long id) {
        pictureService.deletePicture(id);

        return ResponseEntity.noContent().build();
    }

}
