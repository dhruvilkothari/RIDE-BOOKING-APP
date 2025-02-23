package com.dhruvil.project.rideBooking.Ride.Booking.stratergies.implementation;
import com.dhruvil.project.rideBooking.Ride.Booking.entities.RideRequest;
import com.dhruvil.project.rideBooking.Ride.Booking.services.DistanceService;
import com.dhruvil.project.rideBooking.Ride.Booking.stratergies.RideFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideFareSurgePricingFareCalculationStrategy implements RideFareCalculationStrategy {

    private final DistanceService distanceService;
    private static final double SURGE_FACTOR = 2;

    @Override
    public double calculateFare(RideRequest rideRequest) {
        double distance = distanceService.calculateDistance(rideRequest.getPickupLocation(),
                rideRequest.getDropOffLocation());
        return distance*RIDE_FARE_MULTIPLIER*SURGE_FACTOR;
    }
}
