package dev.sethan8r.renttools.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "picture_tool")
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url", nullable = false, unique = true)
    private String url;
}
