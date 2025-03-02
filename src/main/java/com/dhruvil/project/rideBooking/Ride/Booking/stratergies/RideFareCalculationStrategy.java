package com.dhruvil.project.rideBooking.Ride.Booking.stratergies;


import com.dhruvil.project.rideBooking.Ride.Booking.entities.RideRequest;

public interface RideFareCalculationStrategy {

    double RIDE_FARE_MULTIPLIER = 10;

    double calculateFare(RideRequest rideRequest);

}
