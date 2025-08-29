package dev.sethan8r.renttools.service;

import dev.sethan8r.renttools.dto.OrderCreatedDTO;
import dev.sethan8r.renttools.dto.OrderResponseDTO;
import dev.sethan8r.renttools.exception.NotFoundException;
import dev.sethan8r.renttools.mapper.OrderMapper;
import dev.sethan8r.renttools.model.*;
import dev.sethan8r.renttools.repository.DeliveryRepository;
import dev.sethan8r.renttools.repository.OrderRepository;
import dev.sethan8r.renttools.repository.ToolRepository;
import dev.sethan8r.renttools.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ToolRepository toolRepository;
    private final UserRepository userRepository;
    private final DeliveryRepository deliveryRepository;
    private final OrderMapper orderMapper;


    public void createOrder(OrderCreatedDTO orderCreatedDTO) {
        if(orderCreatedDTO.endDate().isBefore(orderCreatedDTO.startDate()))
            throw new IllegalArgumentException("Конечная дата не может быть раньше начальной");

        Tool tool = toolRepository.findById(orderCreatedDTO.toolId()).orElseThrow(
                () -> new NotFoundException("Инструмент с ID " + orderCreatedDTO.toolId() + " не найден"));
        User user = userRepository.findById(orderCreatedDTO.userId()).orElseThrow(
                () -> new NotFoundException("Пользователь с ID " + orderCreatedDTO.userId() + " не найден"));

        Order order = orderMapper.toOrder(orderCreatedDTO, tool, user);

        long daysCount = ChronoUnit.DAYS.between(orderCreatedDTO.startDate(),
                orderCreatedDTO.endDate()) + 1;
        Long fullPrice = daysCount * tool.getPrice();


        order.setStatus(DeliveryStatus.PENDING);
        order.setFullPrice(fullPrice);
        orderRepository.save(order);
    }

    public OrderResponseDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Заказ с ID " + id + " не найден"));

        return orderMapper.toOrderResponseDTO(order);
    }

    public List<OrderResponseDTO> getOrderByUserId(Long id) {
        if(!userRepository.existsById(id)) throw new NotFoundException("Пользователь с ID " + id + " не найден");

        List<Order> lOrder = orderRepository.findByUserId(id);

        List<OrderResponseDTO> lOrderDTO = new ArrayList<>();

        for(Order o : lOrder) {
            lOrderDTO.add(orderMapper.toOrderResponseDTO(o));
        }

        return lOrderDTO;
    }

    public List<OrderResponseDTO> getOrderByStatus(DeliveryStatus status) {

        return orderRepository.findByStatus(status).stream()
                .map(orderMapper::toOrderResponseDTO)
                .collect(Collectors.toList());
    }

    public List<OrderResponseDTO> getOrderByUserIdAndDeliveryStatus(Long id, DeliveryStatus status) {
        if(!userRepository.existsById(id)) throw new NotFoundException("Пользователь с ID " + id + " не найден");

        return orderRepository.findByUserIdAndDeliveryStatus(id, status).stream()
                .map(orderMapper::toOrderResponseDTO)
                .collect(Collectors.toList());
    }

    public List<OrderResponseDTO> getOrderByToolId(Long id) {
        if(!toolRepository.existsById(id)) throw new NotFoundException("Инструмент с ID " + id + " не найден");

        return orderRepository.findByToolId(id).stream()
                .map(orderMapper::toOrderResponseDTO)
                .collect(Collectors.toList());
    }

    public OrderResponseDTO getOrderByDeliveryId(Long id) {
        if(!deliveryRepository.existsById(id)) throw new NotFoundException("Доставка с ID " + id + " не найдена");

        Order order = orderRepository.findByDeliveryId(id).orElseThrow(
                () -> new NotFoundException("Заказ с ID " + id + " не найден"));

        return orderMapper.toOrderResponseDTO(order);
    }

    public void setOrderStatusById(Long id, DeliveryStatus status) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Заказ с ID " + id + " не найден"));

        order.setStatus(status);
        orderRepository.save(order);
    }

    public void setStatusFailedToOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Заказ с ID " + id + " не найден"));
        Delivery delivery = order.getDelivery();

        order.setStatus(DeliveryStatus.FAILED);
        delivery.setStatus(DeliveryStatus.FAILED);
        orderRepository.save(order);
        deliveryRepository.save(delivery);
    }

    public void setStatusCompletedToOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Заказ с ID " + id + " не найден"));
        Delivery delivery = order.getDelivery();

        order.setStatus(DeliveryStatus.COMPLETED);
        delivery.setStatus(DeliveryStatus.COMPLETED);
        orderRepository.save(order);
        deliveryRepository.save(delivery);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
