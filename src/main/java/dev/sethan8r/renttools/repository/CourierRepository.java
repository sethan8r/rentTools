package dev.sethan8r.renttools.repository;

import dev.sethan8r.renttools.model.Courier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourierRepository extends JpaRepository<Courier, Long> {
    Optional<Courier> findByPhone(String phone);
    Optional<Courier> findByEmail(String email);
    Optional<Courier> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
}
