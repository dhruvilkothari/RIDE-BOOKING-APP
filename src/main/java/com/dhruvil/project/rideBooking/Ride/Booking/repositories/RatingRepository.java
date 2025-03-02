package com.dhruvil.project.rideBooking.Ride.Booking.repositories;

import com.dhruvil.project.rideBooking.Ride.Booking.entities.Driver;
import com.dhruvil.project.rideBooking.Ride.Booking.entities.Rating;
import com.dhruvil.project.rideBooking.Ride.Booking.entities.Ride;
import com.dhruvil.project.rideBooking.Ride.Booking.entities.Rider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByRider(Rider rider);
    List<Rating> findByDriver(Driver driver);

    Optional<Rating> findByRide(Ride ride);
}
