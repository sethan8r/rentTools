package dev.sethan8r.renttools.repository;

import dev.sethan8r.renttools.model.Picture;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PictureRepository extends JpaRepository<Picture, Long> {
}
