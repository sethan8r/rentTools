package dev.sethan8r.renttools.service;

import dev.sethan8r.renttools.mapper.UserMapper;
import dev.sethan8r.renttools.model.Courier;
import dev.sethan8r.renttools.model.User;
import dev.sethan8r.renttools.repository.CourierRepository;
import dev.sethan8r.renttools.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final CourierRepository courierRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return convertToUserDetails(user.get());
        }

        Optional<Courier> courier = courierRepository.findByUsername(username);
        if (courier.isPresent()) {
            return convertToUserDetails(courier.get());
        }

        throw new UsernameNotFoundException("Пользователь или курьер с логином '" + username + "' не найден");
    }

    private UserDetails convertToUserDetails(User user) {
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().name().replace("ROLE_", ""))
                .build();
    }

    private UserDetails convertToUserDetails(Courier courier) {
        return org.springframework.security.core.userdetails.User
                .withUsername(courier.getUsername())
                .password(courier.getPassword())
                .roles(courier.getRole().name().replace("ROLE_", ""))
                .build();
    }
}
