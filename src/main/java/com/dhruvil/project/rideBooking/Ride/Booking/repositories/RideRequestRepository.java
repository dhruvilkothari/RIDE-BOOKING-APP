package com.dhruvil.project.rideBooking.Ride.Booking.repositories;

import com.dhruvil.project.rideBooking.Ride.Booking.entities.RideRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RideRequestRepository extends JpaRepository<RideRequest, Long> {

}
