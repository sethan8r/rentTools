package dev.sethan8r.renttools.controller;

import dev.sethan8r.renttools.dto.DeliveryCreateDTO;
import dev.sethan8r.renttools.dto.DeliveryResponseDTO;
import dev.sethan8r.renttools.model.DeliveryStatus;
import dev.sethan8r.renttools.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/delivery")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN', 'COURIER')")
    @PostMapping("/create")
    public DeliveryResponseDTO createDelivery(@RequestBody DeliveryCreateDTO deliveryCreateDTO) {

        return deliveryService.createDelivery(deliveryCreateDTO);
    }

    @GetMapping("/get/{id}")
    public DeliveryResponseDTO getDeliveryById(@PathVariable Long id) {

        return deliveryService.getDeliveryById(id);
    }

    @GetMapping("/get/orderId/{id}")
    public DeliveryResponseDTO getDeliveryByOrderId(@PathVariable Long id) {

        return deliveryService.getDeliveryByOrderId(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN', 'COURIER')")
    @GetMapping("/get/courier/{id}")
    public Page<DeliveryResponseDTO> getDeliveryByCourierId(
            @PathVariable Long id,@PageableDefault(size = 20, sort = "status") Pageable pageable) {

        return deliveryService.getDeliveryByCourierId(id, pageable);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @GetMapping("/get/status")
    public Page<DeliveryResponseDTO> getDeliveryByStatus(
            @RequestParam DeliveryStatus status,@PageableDefault(size = 20, sort = "status") Pageable pageable) {

        return deliveryService.getDeliveryByStatus(status, pageable);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN', 'COURIER')")
    @GetMapping("/get/courierId/{id}/status")
    public Page<DeliveryResponseDTO> getDeliveryByStatusAndCourierId(
            @RequestParam DeliveryStatus status,@PathVariable Long id,
            @PageableDefault(size = 20) Pageable pageable) {
        return deliveryService.getDeliveryByStatusAndCourierId(status, id, pageable);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @PatchMapping("/replace/status/{id}")
    public ResponseEntity<Void> replaceStatusToDeliveryById(@RequestParam DeliveryStatus status,@PathVariable Long id) {
        deliveryService.replaceStatusToDeliveryById(status, id);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN', 'COURIER')")
    @PatchMapping("/set/status/delivered/{id}")
    public ResponseEntity<Void> setStatusDeliveredToDeliveryById(@PathVariable Long id) {
        deliveryService.setStatusDeliveredToDeliveryById(id);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN', 'COURIER')")
    @PatchMapping("/set/status/completed/{id}")
    public ResponseEntity<Void> setStatusCompletedToDeliveryById(@PathVariable Long id) {
        deliveryService.setStatusCompletedToDeliveryById(id);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDelivery(@PathVariable Long id) {
        deliveryService.deleteDelivery(id);

        return ResponseEntity.noContent().build();
    }
}
