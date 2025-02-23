package com.dhruvil.project.rideBooking.Ride.Booking.stratergies.implementation;


import com.dhruvil.project.rideBooking.Ride.Booking.entities.RideRequest;
import com.dhruvil.project.rideBooking.Ride.Booking.services.DistanceService;
import com.dhruvil.project.rideBooking.Ride.Booking.stratergies.RideFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RiderFareDefaultFareCalculationStrategy implements RideFareCalculationStrategy {

    private final DistanceService distanceService;

    @Override
    public double calculateFare(RideRequest rideRequest) {
        double distance = distanceService.calculateDistance(rideRequest.getPickupLocation(),
                rideRequest.getDropOffLocation());
        return distance*RIDE_FARE_MULTIPLIER;
    }
}
