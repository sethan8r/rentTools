package dev.sethan8r.renttools.controller;

import dev.sethan8r.renttools.dto.OrderCreatedDTO;
import dev.sethan8r.renttools.dto.OrderResponseDTO;
import dev.sethan8r.renttools.model.DeliveryStatus;
import dev.sethan8r.renttools.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    public OrderResponseDTO createOrder(@RequestBody OrderCreatedDTO orderCreatedDTO) {

        return orderService.createOrder(orderCreatedDTO);
    }

    @GetMapping("/get/{id}")
    public OrderResponseDTO getOrderById(@PathVariable Long id) {

        return orderService.getOrderById(id);
    }

    @GetMapping("/get/userId/{id}")
    public Page<OrderResponseDTO> getOrderByUserId(
            @PathVariable Long id,@PageableDefault(size = 20, sort = "status") Pageable pageable) {

        return orderService.getOrderByUserId(id, pageable);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN', 'COURIER')")
    @GetMapping("/get/status")
    public Page<OrderResponseDTO> getOrderByStatus(
            @RequestParam DeliveryStatus status,@PageableDefault(size = 20, sort = "status") Pageable pageable) {

        return orderService.getOrderByStatus(status, pageable);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @GetMapping("/get/status/id/{id}")
    public Page<OrderResponseDTO> getOrderByUserIdAndDeliveryStatus(
            @PathVariable Long id,@RequestParam DeliveryStatus status,
            @PageableDefault(size = 20, sort = "status") Pageable pageable) {

        return orderService.getOrderByUserIdAndDeliveryStatus(id, status, pageable);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @GetMapping("/get/toolId/{id}")
    public Page<OrderResponseDTO> getOrderByToolId(
            @PathVariable Long id,@PageableDefault(size = 20, sort = "status") Pageable pageable) {

        return orderService.getOrderByToolId(id, pageable);
    }

    @GetMapping("/get/delivery/{id}")
    public OrderResponseDTO getOrderByDeliveryId(@PathVariable Long id) {

        return orderService.getOrderByDeliveryId(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @PatchMapping("/replace/status/{id}")
    public ResponseEntity<Void> replaceOrderStatusById(@PathVariable Long id,@RequestParam DeliveryStatus status) {
        orderService.replaceOrderStatusById(id, status);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @PatchMapping("/set/status/failed/{id}")
    public ResponseEntity<Void> setStatusFailedToOrderById(@PathVariable Long id) {
        orderService.setStatusFailedToOrderById(id);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @PatchMapping("/set/status/completed/{id}")
    public ResponseEntity<Void> setStatusCompletedToOrderById(@PathVariable Long id) {
        orderService.setStatusCompletedToOrderById(id);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);

        return ResponseEntity.noContent().build();
    }
}
