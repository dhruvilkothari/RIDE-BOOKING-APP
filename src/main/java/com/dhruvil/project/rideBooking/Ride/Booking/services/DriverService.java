package com.dhruvil.project.rideBooking.Ride.Booking.services;


import com.dhruvil.project.rideBooking.Ride.Booking.dto.DriverDto;
import com.dhruvil.project.rideBooking.Ride.Booking.dto.RideDto;
import com.dhruvil.project.rideBooking.Ride.Booking.dto.RiderDto;
import com.dhruvil.project.rideBooking.Ride.Booking.entities.Driver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface DriverService {

    RideDto acceptRide(Long rideRequestId);

    RideDto cancelRide(Long rideId);

    RideDto startRide(Long rideId, String otp);

    RideDto endRide(Long rideId);

    RiderDto rateRider(Long rideId, Integer rating);

    DriverDto getMyProfile();

    Page<RideDto> getAllMyRides(PageRequest pageRequest);

    Driver getCurrentDriver();

    Driver updateDriverAvailability(Driver driver, boolean available);

    Driver createNewDriver(Driver driver);
}
