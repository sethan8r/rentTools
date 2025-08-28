package dev.sethan8r.renttools.service;

import dev.sethan8r.renttools.dto.CourierCreateDTO;
import dev.sethan8r.renttools.dto.CourierResponseDTO;
import dev.sethan8r.renttools.exception.AlreadyExistsException;
import dev.sethan8r.renttools.exception.NotFoundException;
import dev.sethan8r.renttools.mapper.CourierMapper;
import dev.sethan8r.renttools.model.Courier;
import dev.sethan8r.renttools.model.Role;
import dev.sethan8r.renttools.repository.CourierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourierService {

    private final CourierRepository courierRepository;
    private final CourierMapper courierMapper;
    private final PasswordEncoder passwordEncoder;

    public void createCourier(CourierCreateDTO courierCreateDTO) {
        if (courierRepository.existsByUsername(courierCreateDTO.username())) {
            throw new AlreadyExistsException("Курьер с логином " + courierCreateDTO.username() + "существует");
        }

        if (courierRepository.existsByEmail(courierCreateDTO.email())) {
            throw new AlreadyExistsException("Курьер с почтой " + courierCreateDTO.email() + "существует");
        }

        if (courierRepository.existsByPhone(courierCreateDTO.phone())) {
            throw new AlreadyExistsException("Курьер с телефоном " + courierCreateDTO.phone() + "существует");
        }

        Courier courier = courierMapper.toCourier(courierCreateDTO);
        courier.setDelivers(0);
        courier.setPassword(passwordEncoder.encode(courierCreateDTO.password()));
        courier.setRole(Role.ROLE_COURIER);

        courierRepository.save(courier);
    }

    public CourierResponseDTO getCourierById(Long id) {
        Courier courier = courierRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Курьер с ID " + id + " не найден"));

        return courierMapper.toCourierResponseDTO(courier);
    }

    public CourierResponseDTO getCourierByUsername(String username) {
        Courier courier = courierRepository.findByUsername(username).orElseThrow(
                () -> new NotFoundException("Курьер с логином " + username + " не найден"));

        return courierMapper.toCourierResponseDTO(courier);
    }

    public CourierResponseDTO getCourierByPhone(String phone) {
        Courier courier = courierRepository.findByPhone(phone).orElseThrow(
                () -> new NotFoundException("Курьер с телефоном " + phone + " не найден"));

        return courierMapper.toCourierResponseDTO(courier);
    }

    public void replaceUsernameToCourier(Long id, String username) {
        if (courierRepository.existsByUsername(username)) {
            throw new AlreadyExistsException("Курьер с логином " + username + "существует");
        }

        Courier courier = courierRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Курьер с ID " + id + " не найден"));

        courier.setUsername(username);
        courierRepository.save(courier);
    }

    public void replacePhoneToCourier(Long id, String phone) {
        if (courierRepository.existsByPhone(phone)) {
            throw new AlreadyExistsException("Курьер с телефоном " + phone + "существует");
        }

        Courier courier = courierRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Курьер с ID " + id + " не найден"));

        courier.setPhone(phone);
        courierRepository.save(courier);
    }

    public void replaceEmailToCourier(Long id, String email) {
        if (courierRepository.existsByEmail(email)) {
            throw new AlreadyExistsException("Курьер с телефоном " + email + "существует");
        }

        Courier courier = courierRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Курьер с ID " + id + " не найден"));

        courier.setEmail(email);
        courierRepository.save(courier);
    }

    public void replacePasswordToCourier(Long id, String password) {
        Courier courier = courierRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Курьер с ID " + id + " не найден"));

        courier.setPassword(passwordEncoder.encode(password));
        courierRepository.save(courier);
    }

    public void replaceNameToCourier(Long id, String firstName) {
        Courier courier = courierRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Курьер с ID " + id + " не найден"));

        courier.setFirstName(firstName);
        courierRepository.save(courier);
    }

    public void replaceLastNameToCourier(Long id, String lastName) {
        Courier courier = courierRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Курьер с ID " + id + " не найден"));

        courier.setLastName(lastName);
        courierRepository.save(courier);
    }

    public void addDeliversToCourier(Long id) {
        Courier courier = courierRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Курьер с ID " + id + " не найден"));

        courier.setDelivers(courier.getDelivers() + 1);
        courierRepository.save(courier);
    }

    public void replaceDeliversToCourier(Long id, Integer delivers) {
        Courier courier = courierRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Курьер с ID " + id + " не найден"));

        courier.setDelivers(delivers);
        courierRepository.save(courier);
    }

}
