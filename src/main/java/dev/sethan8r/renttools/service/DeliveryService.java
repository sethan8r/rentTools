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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final CourierRepository courierRepository;
    private final OrderRepository orderRepository;
    private final DeliveryMapper deliveryMapper;


    @Transactional
    public DeliveryResponseDTO createDelivery(DeliveryCreateDTO deliveryCreateDTO) {
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

        return deliveryMapper.toDeliveryResponseDTO(delivery);
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

    public Page<DeliveryResponseDTO> getDeliveryByCourierId(Long id, Pageable pageable) {
        if(!courierRepository.existsById(id)) throw new NotFoundException("Курьер с ID " + id + " не найден");

        return deliveryRepository.findByCourierId(id, pageable)
                .map(deliveryMapper::toDeliveryResponseDTO);
    }

    public Page<DeliveryResponseDTO> getDeliveryByStatus(DeliveryStatus status, Pageable pageable) {

        return deliveryRepository.findByStatus(status, pageable)
                .map(deliveryMapper::toDeliveryResponseDTO);
    }

    public Page<DeliveryResponseDTO> getDeliveryByStatusAndCourierId(DeliveryStatus status, Long id, Pageable pageable) {
        if(!courierRepository.existsById(id)) throw new NotFoundException("Курьер с ID " + id + " не найден");

        return deliveryRepository.findByStatusAndCourier_id(status, id, pageable) //findByStatusAndCourierId - было изначально так
                .map(deliveryMapper::toDeliveryResponseDTO);
    }

    @Transactional
    public void replaceStatusToDeliveryById(DeliveryStatus status, Long id) {
        Delivery delivery = deliveryRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Доставка с ID заказа " + id + " не найдена"));

        delivery.setStatus(status);
        deliveryRepository.save(delivery);
    }

    @Transactional
    public void setStatusDeliveredToDeliveryById(Long id) {
        Delivery delivery = deliveryRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Доставка с ID заказа " + id + " не найдена"));
        Order order = delivery.getOrder();
        Courier courier = delivery.getCourier();

        delivery.setStatus(DeliveryStatus.DELIVERED);
        order.setStatus(DeliveryStatus.DELIVERED);
        courier.setDelivers(courier.getDelivers() + 1);

        deliveryRepository.save(delivery);
        orderRepository.save(order);
        courierRepository.save(courier);
    }

    @Transactional
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
