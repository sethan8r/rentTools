package dev.sethan8r.renttools.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tool_tool")
public class Tool {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "price", nullable = false)
    private Long price;

    @OneToOne
    @JoinColumn(name = "picture_id", nullable = false)
    private Picture picture;

    @Column(name = "is_available")
    private Boolean isAvailable;
}
