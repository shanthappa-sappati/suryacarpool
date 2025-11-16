package com.surya.carpool.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "rides")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class Ride {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fromLocation;
    private String toLocation;
    private LocalDateTime departureTime;
    private Integer availableSeats;
    private Double fare;

    @ManyToOne(fetch = FetchType.LAZY)
    private User driver;
}
