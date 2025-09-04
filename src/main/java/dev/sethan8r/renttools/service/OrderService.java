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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ToolRepository toolRepository;
    private final UserRepository userRepository;
    private final DeliveryRepository deliveryRepository;
    private final OrderMapper orderMapper;

    @Transactional
    public OrderResponseDTO createOrder(OrderCreatedDTO orderCreatedDTO) {
        if(orderCreatedDTO.endDate().isBefore(orderCreatedDTO.startDate()))
            throw new IllegalArgumentException("Конечная дата не может быть раньше начальной");

        Tool tool = toolRepository.findById(orderCreatedDTO.toolId()).orElseThrow(
                () -> new NotFoundException("Инструмент с ID " + orderCreatedDTO.toolId() + " не найден"));
        User user = userRepository.findById(orderCreatedDTO.userId()).orElseThrow(
                () -> new NotFoundException("Пользователь с ID " + orderCreatedDTO.userId() + " не найден"));

        Order order = orderMapper.toOrder(orderCreatedDTO, tool, user);
        tool.setIsAvailable(false);

        long daysCount = ChronoUnit.DAYS.between(orderCreatedDTO.startDate(),
                orderCreatedDTO.endDate()) + 1;
        Long fullPrice = daysCount * tool.getPrice();


        order.setStatus(DeliveryStatus.PENDING);
        order.setFullPrice(fullPrice);
        orderRepository.save(order);

        return orderMapper.toOrderResponseDTO(order);
    }

    public OrderResponseDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Заказ с ID " + id + " не найден"));

        return orderMapper.toOrderResponseDTO(order);
    }


    public Page<OrderResponseDTO> getOrderByUserId(Long id, Pageable pageable) {
        if(!userRepository.existsById(id)) throw new NotFoundException("Пользователь с ID " + id + " не найден");


        return orderRepository.findByUserId(id, pageable).map(orderMapper::toOrderResponseDTO);
    }

    public Page<OrderResponseDTO> getOrderByStatus(DeliveryStatus status, Pageable pageable) {

        return orderRepository.findByStatus(status, pageable)
                .map(orderMapper::toOrderResponseDTO);
    }

    public Page<OrderResponseDTO> getOrderByUserIdAndDeliveryStatus(Long id, DeliveryStatus status, Pageable pageable) {
        if(!userRepository.existsById(id)) throw new NotFoundException("Пользователь с ID " + id + " не найден");

        return orderRepository.findByUserIdAndDeliveryStatus(id, status, pageable)
                .map(orderMapper::toOrderResponseDTO);
    }

    public Page<OrderResponseDTO> getOrderByToolId(Long id, Pageable pageable) {
        if(!toolRepository.existsById(id)) throw new NotFoundException("Инструмент с ID " + id + " не найден");

        return orderRepository.findByToolId(id, pageable)
                .map(orderMapper::toOrderResponseDTO);
    }

    public OrderResponseDTO getOrderByDeliveryId(Long id) {
        if(!deliveryRepository.existsById(id)) throw new NotFoundException("Доставка с ID " + id + " не найдена");

        Order order = orderRepository.findByDeliveryId(id).orElseThrow(
                () -> new NotFoundException("Заказ с ID " + id + " не найден"));

        return orderMapper.toOrderResponseDTO(order);
    }

    @Transactional
    public void replaceOrderStatusById(Long id, DeliveryStatus status) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Заказ с ID " + id + " не найден"));

        order.setStatus(status);
        orderRepository.save(order);
    }

    @Transactional
    public void setStatusFailedToOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Заказ с ID " + id + " не найден"));
        Delivery delivery = order.getDelivery();

        order.setStatus(DeliveryStatus.FAILED);
        delivery.setStatus(DeliveryStatus.FAILED);
        orderRepository.save(order);
        deliveryRepository.save(delivery);
    }

    @Transactional
    public void setStatusCompletedToOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Заказ с ID " + id + " не найден"));
        Delivery delivery = order.getDelivery();
        Tool tool = order.getTool();

        tool.setIsAvailable(true);
        order.setStatus(DeliveryStatus.COMPLETED);
        delivery.setStatus(DeliveryStatus.COMPLETED);
        orderRepository.save(order);
        deliveryRepository.save(delivery);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
