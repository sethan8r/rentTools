package dev.sethan8r.renttools.controller;

import dev.sethan8r.renttools.dto.CourierCreateDTO;
import dev.sethan8r.renttools.dto.CourierResponseDTO;
import dev.sethan8r.renttools.dto.PasswordChangeRequest;
import dev.sethan8r.renttools.service.CourierService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courier")
@RequiredArgsConstructor
public class CourierController {

    private final CourierService courierService;

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @PostMapping("/create")
    public ResponseEntity<Void> createCourier(@RequestBody CourierCreateDTO courierCreateDTO) {
        courierService.createCourier(courierCreateDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN', 'COURIER')")
    @GetMapping("/get/{id}")
    public CourierResponseDTO getCourierById(@PathVariable Long id) {

        return courierService.getCourierById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @GetMapping("/get/username")
    public CourierResponseDTO getCourierByUsername(@RequestParam String username) {

        return courierService.getCourierByUsername(username);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @GetMapping("/get/phone")
    public CourierResponseDTO getCourierByPhone(@RequestParam String phone) {

        return courierService.getCourierByPhone(phone);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @PatchMapping("/replace/username/{id}")
    public ResponseEntity<Void> replaceUsernameToCourier(@PathVariable Long id, @RequestParam String username) {
        courierService.replaceUsernameToCourier(id, username);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @PatchMapping("/replace/phone/{id}")
    public ResponseEntity<Void> replacePhoneToCourier(@PathVariable Long id, @RequestParam String phone) {
        courierService.replacePhoneToCourier(id, phone);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @PatchMapping("/replace/email/{id}")
    public ResponseEntity<Void> replaceEmailToCourier(@PathVariable Long id, @RequestParam String email) {
        courierService.replaceEmailToCourier(id, email);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('COURIER')")
    @PatchMapping("/replace/password/{id}")
    public ResponseEntity<Void> replacePasswordToCourier(@PathVariable Long id, @RequestBody PasswordChangeRequest request) {
        courierService.replacePasswordToCourier(id, request.password());

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @PatchMapping("/replace/firstname/{id}")
    public ResponseEntity<Void> replaceNameToCourier(@PathVariable Long id, @RequestParam String firstName) {
        courierService.replaceNameToCourier(id, firstName);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @PatchMapping("/replace/lastname/{id}")
    public ResponseEntity<Void> replaceLastNameToCourier(@PathVariable Long id, @RequestParam String lastName) {
        courierService.replaceLastNameToCourier(id, lastName);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @PatchMapping("/delivers/add/{id}")
    public ResponseEntity<Void> addDeliversToCourier(@PathVariable Long id) {
        courierService.addDeliversToCourier(id);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @PatchMapping("/replace/delivery/{id}")
    public ResponseEntity<Void> replaceDeliversToCourier(@PathVariable Long id, @RequestParam Integer delivers) {
        courierService.replaceDeliversToCourier(id, delivers);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('SUPERADMIN')")
    @GetMapping("/get/all")
    public Page<CourierResponseDTO> getAllCourier(
            @PageableDefault(size = 20, sort = {"firstName", "lastName"}) Pageable pageable) {

        return courierService.getAllCourier(pageable);
    }
}
