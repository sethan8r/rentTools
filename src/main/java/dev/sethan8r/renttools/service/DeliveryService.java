package dev.sethan8r.renttools.service;

import dev.sethan8r.renttools.mapper.DeliveryMapper;
import dev.sethan8r.renttools.repository.CourierRepository;
import dev.sethan8r.renttools.repository.DeliveryRepository;
import dev.sethan8r.renttools.repository.OrderRepository;
import dev.sethan8r.renttools.repository.UserRepository;

public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final UserRepository userRepository;
    private final CourierRepository courierRepository;
    private final OrderRepository orderRepository;

    private final DeliveryMapper deliveryMapper;

    public DeliveryService(DeliveryRepository deliveryRepository, UserRepository userRepository,
                           CourierRepository courierRepository, OrderRepository orderRepository,
                           DeliveryMapper deliveryMapper) {
        this.deliveryRepository = deliveryRepository;
        this.userRepository = userRepository;
        this.courierRepository = courierRepository;
        this.orderRepository = orderRepository;
        this.deliveryMapper = deliveryMapper;
    }


}
