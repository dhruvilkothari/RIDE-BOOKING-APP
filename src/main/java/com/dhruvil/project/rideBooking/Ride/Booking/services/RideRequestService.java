package com.dhruvil.project.rideBooking.Ride.Booking.services;


import com.dhruvil.project.rideBooking.Ride.Booking.entities.RideRequest;

public interface RideRequestService {

    RideRequest findRideRequestById(Long rideRequestId);

    void update(RideRequest rideRequest);
}
