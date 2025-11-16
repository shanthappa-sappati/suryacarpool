package com.surya.carpool.repository;

import com.surya.carpool.model.Ride;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RideRepository extends JpaRepository<Ride, Long> {
    List<Ride> findByFromLocationContainingIgnoreCaseOrToLocationContainingIgnoreCase(String from, String to);
}
