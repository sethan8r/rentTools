package dev.sethan8r.renttools.controller;

import dev.sethan8r.renttools.dto.PasswordChangeRequest;
import dev.sethan8r.renttools.dto.UserCreateDTO;
import dev.sethan8r.renttools.dto.UserResponseDTO;
import dev.sethan8r.renttools.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Void> createUser(@RequestBody UserCreateDTO userCreateDTO) {
        userService.createUser(userCreateDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/replace/phone/{id}")
    public ResponseEntity<Void> replacePhoneToUser(@PathVariable Long id, @RequestParam String phone) {
        userService.replacePhoneToUser(id, phone);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/replace/password/{id}")
    public ResponseEntity<Void> replacePasswordToUser(@PathVariable Long id, @RequestBody PasswordChangeRequest request) {
        userService.replacePasswordToUser(id, request.password());

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/replace/firstname/{id}")
    public ResponseEntity<Void> replaceNameToUser(@PathVariable Long id, @RequestParam String firstName) {
        userService.replaceNameToUser(id, firstName);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/replace/lastname/{id}")
    public ResponseEntity<Void> replaceLastNameToUser(@PathVariable Long id, @RequestParam String lastName) {
        userService.replaceLastNameToUser(id, lastName);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/replace/email/{id}")
    public ResponseEntity<Void> replaceEmailToUser(@PathVariable Long id, @RequestParam String email) {
        userService.replaceEmailToUser(id, email);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/replace/username/{id}")
    public ResponseEntity<Void> replaceUsernameToUser(@PathVariable Long id, @RequestParam String username) {
        userService.replaceUsernameToUser(id, username);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @GetMapping("/get/username")
    public UserResponseDTO getUserByUsername (@RequestParam String username) {

        return userService.getUserByUsername(username);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @GetMapping("/get/email")
    public UserResponseDTO getUserByEmail(@RequestParam String email) {

        return userService.getUserByEmail(email);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    @GetMapping("/get/phone")
    public UserResponseDTO getUserByPhone(@RequestParam String phone) {

        return userService.getUserByPhone(phone);
    }

    @GetMapping("/get/{id}")
    public UserResponseDTO getUserById(@PathVariable Long id) {

        return userService.getUserById(id);
    }

    @PreAuthorize("hasRole('SUPERADMIN')")
    @GetMapping("/get/all")
    public Page<UserResponseDTO> getAllUser(
            @PageableDefault(size = 20, sort = {"firstName", "lastName"}) Pageable pageable) {

        return userService.getAllUser(pageable);
    }
}
