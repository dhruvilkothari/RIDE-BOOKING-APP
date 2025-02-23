package com.dhruvil.project.rideBooking.Ride.Booking.services;

import com.dhruvil.project.rideBooking.Ride.Booking.dto.DriverDto;
import com.dhruvil.project.rideBooking.Ride.Booking.dto.RideDto;
import com.dhruvil.project.rideBooking.Ride.Booking.dto.RideRequestDto;
import com.dhruvil.project.rideBooking.Ride.Booking.dto.RiderDto;
import com.dhruvil.project.rideBooking.Ride.Booking.entities.Rider;
import com.dhruvil.project.rideBooking.Ride.Booking.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface RiderService {

    RideRequestDto requestRide(RideRequestDto rideRequestDto);

    RideDto cancelRide(Long rideId);

    DriverDto rateDriver(Long rideId, Integer rating);

    RiderDto getMyProfile();

    Page<RideDto> getAllMyRides(PageRequest pageRequest);

    Rider createNewRider(User user);

    Rider getCurrentRider();
}
