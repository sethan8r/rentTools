package dev.sethan8r.renttools.repository;

import dev.sethan8r.renttools.model.Delivery;
import dev.sethan8r.renttools.model.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    Optional<Delivery> findByOrderId(Long orderId);
    List<Delivery> findByCourierId(Long courierId);
    List<Delivery> findByStatus(DeliveryStatus status);
    List<Delivery> findByStatusAndCourierId(DeliveryStatus status, Long courierId);
    boolean existsByOrderId(Long orderId);
}
