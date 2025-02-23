package com.dhruvil.project.rideBooking.Ride.Booking.stratergies.implementation;


import com.dhruvil.project.rideBooking.Ride.Booking.entities.Driver;
import com.dhruvil.project.rideBooking.Ride.Booking.entities.RideRequest;
import com.dhruvil.project.rideBooking.Ride.Booking.repositories.DriverRepository;
import com.dhruvil.project.rideBooking.Ride.Booking.stratergies.DriverMatchingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DriverMatchingNearestDriverStrategy implements DriverMatchingStrategy {

    private final DriverRepository driverRepository;

    @Override
    public List<Driver> findMatchingDriver(RideRequest rideRequest) {
        return driverRepository.findTenNearestDrivers(rideRequest.getPickupLocation());
    }
}
