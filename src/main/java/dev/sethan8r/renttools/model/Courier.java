package dev.sethan8r.renttools.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "courier_tool")
public class Courier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login", unique = true, nullable = false)
    private String username;

    @Column(name = "delivers")
    private Integer delivers;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "first_name",nullable = false)
    private String firstName;

    @Column(name = "last_name",nullable = false)
    private String lastName;

    @Column(name = "phone", unique = true, nullable = false)
    private String phone;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false, columnDefinition = "VARCHAR(50) NOT NULL DEFAULT 'ROLE_COURIER'")
    private Role role;
}
