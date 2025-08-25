package dev.sethan8r.renttools.repository;

import dev.sethan8r.renttools.model.Delivery;
import dev.sethan8r.renttools.model.DeliveryStatus;
import dev.sethan8r.renttools.model.Order;
import dev.sethan8r.renttools.model.Tool;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
    List<Order> findByStatus(DeliveryStatus status);
    List<Order> findByUserIdAndDeliveryStatus(Long userId, DeliveryStatus status);
    List<Order> findByToolId(Long toolId);
    Optional<Order> findByDeliveryId(Long deliveryId);
}
