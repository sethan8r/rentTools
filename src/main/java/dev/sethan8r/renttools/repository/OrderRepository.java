package dev.sethan8r.renttools.repository;

import dev.sethan8r.renttools.model.DeliveryStatus;
import dev.sethan8r.renttools.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByUserId(Long userId, Pageable pageable);
    Page<Order> findByStatus(DeliveryStatus status, Pageable pageable);
    Page<Order> findByUserIdAndDeliveryStatus(Long userId, DeliveryStatus status, Pageable pageable);
    Page<Order> findByToolId(Long toolId, Pageable pageable);
    Optional<Order> findByDeliveryId(Long deliveryId);
}
