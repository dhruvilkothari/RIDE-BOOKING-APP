package com.dhruvil.project.rideBooking.Ride.Booking.services.implementation;

import com.dhruvil.project.rideBooking.Ride.Booking.entities.Driver;
import com.dhruvil.project.rideBooking.Ride.Booking.entities.Ride;
import com.dhruvil.project.rideBooking.Ride.Booking.entities.RideRequest;
import com.dhruvil.project.rideBooking.Ride.Booking.entities.Rider;
import com.dhruvil.project.rideBooking.Ride.Booking.entities.enums.RideStatus;
import com.dhruvil.project.rideBooking.Ride.Booking.services.RideService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class RideServiceImpl implements RideService {
    @Override
    public Ride getRideById(Long rideId) {
        return null;
    }

    @Override
    public Ride createNewRide(RideRequest rideRequest, Driver driver) {
        return null;
    }

    @Override
    public Ride updateRideStatus(Ride ride, RideStatus rideStatus) {
        return null;
    }

    @Override
    public Page<Ride> getAllRidesOfRider(Rider rider, PageRequest pageRequest) {
        return null;
    }

    @Override
    public Page<Ride> getAllRidesOfDriver(Driver driver, PageRequest pageRequest) {
        return null;
    }
}
