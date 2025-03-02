package com.dhruvil.project.rideBooking.Ride.Booking.services;

import com.dhruvil.project.rideBooking.Ride.Booking.entities.Driver;
import com.dhruvil.project.rideBooking.Ride.Booking.entities.Ride;
import com.dhruvil.project.rideBooking.Ride.Booking.entities.RideRequest;
import com.dhruvil.project.rideBooking.Ride.Booking.entities.Rider;
import com.dhruvil.project.rideBooking.Ride.Booking.entities.enums.RideStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface RideService {

    Ride getRideById(Long rideId);

    Ride createNewRide(RideRequest rideRequest, Driver driver);

    Ride updateRideStatus(Ride ride, RideStatus rideStatus);

    Page<Ride> getAllRidesOfRider(Rider rider, PageRequest pageRequest);

    Page<Ride> getAllRidesOfDriver(Driver driver, PageRequest pageRequest);
}
