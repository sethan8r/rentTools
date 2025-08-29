package dev.sethan8r.renttools.service;

import dev.sethan8r.renttools.dto.DeliveryCreateDTO;
import dev.sethan8r.renttools.dto.DeliveryResponseDTO;
import dev.sethan8r.renttools.exception.AlreadyExistsException;
import dev.sethan8r.renttools.exception.NotFoundException;
import dev.sethan8r.renttools.mapper.DeliveryMapper;
import dev.sethan8r.renttools.model.*;
import dev.sethan8r.renttools.repository.CourierRepository;
import dev.sethan8r.renttools.repository.DeliveryRepository;
import dev.sethan8r.renttools.repository.OrderRepository;
import dev.sethan8r.renttools.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final UserRepository userRepository;
    private final CourierRepository courierRepository;
    private final OrderRepository orderRepository;
    private final DeliveryMapper deliveryMapper;



    public void createDelivery(DeliveryCreateDTO deliveryCreateDTO) {
        if (deliveryRepository.existsByOrderId(deliveryCreateDTO.orderId())) {
            throw new AlreadyExistsException("Доставка для этого заказа уже назначена");
        }

        Order order = orderRepository.findById(deliveryCreateDTO.orderId()).orElseThrow(
                () -> new NotFoundException("Заказ с ID " + deliveryCreateDTO.orderId() + " не найден"));
        Courier courier = courierRepository.findById(deliveryCreateDTO.courierId()).orElseThrow(
                () -> new NotFoundException("Курьер с ID " + deliveryCreateDTO.courierId() + " не найден"));
        User user = order.getUser();

        Delivery delivery = deliveryMapper.toDelivery(deliveryCreateDTO, courier, order);

        delivery.setAddress(order.getAddress());
        delivery.setPhoneCourier(courier.getPhone());
        delivery.setPhone(user.getPhone());
        delivery.setStatus(DeliveryStatus.IN_PROGRESS);
        order.setStatus(DeliveryStatus.IN_PROGRESS);

        deliveryRepository.save(delivery);
        orderRepository.save(order);
    }

    public DeliveryResponseDTO getDeliveryById(Long id) {
        Delivery delivery = deliveryRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Доставка с ID " + id + " не найдена"));

        return deliveryMapper.toDeliveryResponseDTO(delivery);
    }

    public DeliveryResponseDTO getDeliveryByOrderId(Long id) {
        Delivery delivery = deliveryRepository.findByOrderId(id).orElseThrow(
                () -> new NotFoundException("Доставка с ID заказа " + id + " не найдена"));

        return deliveryMapper.toDeliveryResponseDTO(delivery);
    }

    public List<DeliveryResponseDTO> getDeliveryByCourierId(Long id) {
        if(!courierRepository.existsById(id)) throw new NotFoundException("Курьер с ID " + id + " не найден");

        return deliveryRepository.findByCourierId(id).stream()
                .map(deliveryMapper::toDeliveryResponseDTO)
                .collect(Collectors.toList());
    }

    public List<DeliveryResponseDTO> getDeliveryByStatus(DeliveryStatus status) {

        return deliveryRepository.findByStatus(status).stream()
                .map(deliveryMapper::toDeliveryResponseDTO)
                .collect(Collectors.toList());
    }

    public List<DeliveryResponseDTO> getDeliveryByStatusAndCourierId(DeliveryStatus status, Long id) {
        if(!courierRepository.existsById(id)) throw new NotFoundException("Курьер с ID " + id + " не найден");

        return deliveryRepository.findByStatusAndCourierId(status, id).stream()
                .map(deliveryMapper::toDeliveryResponseDTO)
                .collect(Collectors.toList());
    }

    public void setStatusToDeliveryById(DeliveryStatus status, Long id) {
        Delivery delivery = deliveryRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Доставка с ID заказа " + id + " не найдена"));

        delivery.setStatus(status);
        deliveryRepository.save(delivery);
    }

    public void setStatusDeliveredToDeliveryById(Long id) {
        Delivery delivery = deliveryRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Доставка с ID заказа " + id + " не найдена"));
        Order order = delivery.getOrder();

        delivery.setStatus(DeliveryStatus.DELIVERED);
        order.setStatus(DeliveryStatus.DELIVERED);

        deliveryRepository.save(delivery);
        orderRepository.save(order);
    }

    public void setStatusCompletedToDeliveryById(Long id) {
        Delivery delivery = deliveryRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Доставка с ID заказа " + id + " не найдена"));
        Order order = delivery.getOrder();

        delivery.setStatus(DeliveryStatus.COMPLETED);
        order.setStatus(DeliveryStatus.COMPLETED);

        deliveryRepository.save(delivery);
        orderRepository.save(order);
    }

    public void deleteDelivery(Long id) {
        deliveryRepository.deleteById(id);
    }
}
