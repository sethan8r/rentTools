package dev.sethan8r.renttools.repository;

import dev.sethan8r.renttools.model.Delivery;
import dev.sethan8r.renttools.model.DeliveryStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    Optional<Delivery> findByOrderId(Long orderId);
    Page<Delivery> findByCourierId(Long courierId, Pageable pageable);
    Page<Delivery> findByStatus(DeliveryStatus status, Pageable pageable);
    Page<Delivery> findByStatusAndCourier_id(DeliveryStatus status, Long courier, Pageable pageable); //findByStatusAndCourierId - было изначально так
    boolean existsByOrderId(Long orderId);
}
