package com.dhruvil.project.rideBooking.Ride.Booking.repositories;

import com.dhruvil.project.rideBooking.Ride.Booking.entities.Ride;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RideRepository extends JpaRepository<Ride,Long> {
}
