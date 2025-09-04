package dev.sethan8r.renttools.service;

import dev.sethan8r.renttools.model.Order;
import dev.sethan8r.renttools.model.User;
import dev.sethan8r.renttools.repository.OrderRepository;
import dev.sethan8r.renttools.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderSecurityService { //Что-бы рандомный чел не вошел на чужой заказ

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public boolean isOrderOwner(Long orderId, String username) {
        // Находим заказ
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isEmpty()) {
            return false; // Заказ не существует
        }

        // Находим пользователя
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            return false; // Пользователь не существует
        }

        Order order = orderOpt.get();
        User user = userOpt.get();

        // Проверяем, что заказ принадлежит пользователю
        return order.getUser().getId().equals(user.getId());
    }
}