package dev.sethan8r.renttools.service;

import dev.sethan8r.renttools.dto.UserCreateDTO;
import dev.sethan8r.renttools.dto.UserResponseDTO;
import dev.sethan8r.renttools.exception.AlreadyExistsException;
import dev.sethan8r.renttools.exception.NotFoundException;
import dev.sethan8r.renttools.mapper.UserMapper;
import dev.sethan8r.renttools.model.Role;
import dev.sethan8r.renttools.model.User;
import dev.sethan8r.renttools.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public void createUser(UserCreateDTO userCreateDTO) {
        if (userRepository.existsByUsername(userCreateDTO.username())) {
            System.out.println("ТУт");
            throw new AlreadyExistsException("Пользователь с логином " + userCreateDTO.username() + " существует");
        }

        if (userRepository.existsByEmail(userCreateDTO.email())) {
            throw new AlreadyExistsException("Пользователь с почтой " + userCreateDTO.email() + " существует");
        }

        if (userRepository.existsByPhone(userCreateDTO.phone())) {
            throw new AlreadyExistsException("Пользователь с телефоном " + userCreateDTO.phone() + " существует");
        }

        User user = userMapper.toUser(userCreateDTO);
        user.setRole(Role.ROLE_USER);
        user.setPassword(passwordEncoder.encode(userCreateDTO.password()));

        userRepository.save(user);
    }

    @Transactional
    public void replacePhoneToUser(Long id, String phone) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Пользователь с ID " + id + " не найден"));

        if (userRepository.existsByPhone(phone)) {
            throw new AlreadyExistsException("Пользователь с телефоном " + phone + " существует");
        }

        user.setPhone(phone);
        userRepository.save(user);
    }

    @Transactional
    public void replacePasswordToUser(Long id, String password) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Пользователь с ID " + id + " не найден"));

        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    @Transactional
    public void replaceNameToUser(Long id, String firstName) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Пользователь с ID " + id + " не найден"));

        user.setFirstName(firstName);
        userRepository.save(user);
    }

    @Transactional
    public void replaceLastNameToUser(Long id, String lastName) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Пользователь с ID " + id + " не найден"));

        user.setLastName(lastName);
        userRepository.save(user);
    }

    @Transactional
    public void replaceEmailToUser(Long id, String email) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Пользователь с ID " + id + " не найден"));

        if (userRepository.existsByEmail(email)) {
            throw new AlreadyExistsException("Пользователь с почтой " + email + " существует");
        }

        user.setEmail(email);
        userRepository.save(user);
    }

    @Transactional
    public void replaceUsernameToUser(Long id, String username) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Пользователь с ID " + id + " не найден"));

        if (userRepository.existsByUsername(username)) {
            throw new AlreadyExistsException("Пользователь с логином " + username + " существует");
        }

        user.setUsername(username);
        userRepository.save(user);
    }

    public UserResponseDTO getUserByUsername (String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new NotFoundException("Пользователь с логином " + username + " не найден"));

        return userMapper.toUserResponseDTO(user);
    }

    public UserResponseDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("Пользователь с почтой " + email + " не найден"));

        return userMapper.toUserResponseDTO(user);
    }

    public UserResponseDTO getUserByPhone(String phone) {
        User user = userRepository.findByPhone(phone).orElseThrow(
                () -> new NotFoundException("Пользователь с телефоном " + phone + " не найден"));

        return userMapper.toUserResponseDTO(user);
    }

    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Пользователь с ID " + id + " не найден"));

        return userMapper.toUserResponseDTO(user);
    }

    public Page<UserResponseDTO> getAllUser(Pageable pageable) {

        return userRepository.findAll(pageable).map(userMapper::toUserResponseDTO);
    }
}
