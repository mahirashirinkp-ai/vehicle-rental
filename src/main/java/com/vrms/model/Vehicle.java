package com.vrms.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "vehicles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;         // e.g. Toyota Innova

    @Column(nullable = false)
    private String type;         // Car, Bike, Van, etc.

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private double pricePerDay;

    @Column(nullable = false)
    private boolean available = true;

    private String imageUrl;     // optional image link

    private String description;
}
