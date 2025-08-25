package dev.sethan8r.renttools.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "delivery_tool")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "courier_id", nullable = false)
    private Courier courier;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "phone_courier", nullable = false)
    private String phoneCourier;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private DeliveryStatus status;
}
