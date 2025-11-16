package com.surya.carpool.controller;

import com.surya.carpool.model.Booking;
import com.surya.carpool.model.Ride;
import com.surya.carpool.model.User;
import com.surya.carpool.repository.BookingRepository;
import com.surya.carpool.repository.RideRepository;
import com.surya.carpool.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    private final BookingRepository bookingRepo;
    private final RideRepository rideRepo;
    private final UserRepository userRepo;

    public BookingController(BookingRepository bookingRepo, RideRepository rideRepo, UserRepository userRepo) {
        this.bookingRepo = bookingRepo;
        this.rideRepo = rideRepo;
        this.userRepo = userRepo;
    }

    @PostMapping
    public ResponseEntity<?> bookRide(@RequestBody Booking b) {
        if (b.getRide() == null || b.getRide().getId() == null) return ResponseEntity.badRequest().body("Ride id required");
        if (b.getPassenger() == null || b.getPassenger().getId() == null) return ResponseEntity.badRequest().body("Passenger id required");

        Optional<Ride> rideOpt = rideRepo.findById(b.getRide().getId());
        Optional<User> passengerOpt = userRepo.findById(b.getPassenger().getId());

        if (rideOpt.isEmpty()) return ResponseEntity.badRequest().body("Ride not found");
        if (passengerOpt.isEmpty()) return ResponseEntity.badRequest().body("Passenger not found");

        Ride ride = rideOpt.get();
        if (ride.getAvailableSeats() == null || ride.getAvailableSeats() < b.getSeatsBooked()) {
            return ResponseEntity.badRequest().body("Not enough seats");
        }

        ride.setAvailableSeats(ride.getAvailableSeats() - b.getSeatsBooked());
        rideRepo.save(ride);

        b.setRide(ride);
        b.setPassenger(passengerOpt.get());
        Booking saved = bookingRepo.save(b);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public java.util.List<Booking> listBookings() {
        return bookingRepo.findAll();
    }
}
