package com.dhruvil.project.rideBooking.Ride.Booking.services.implementation;

import com.dhruvil.project.rideBooking.Ride.Booking.dto.DriverDto;
import com.dhruvil.project.rideBooking.Ride.Booking.dto.RideDto;
import com.dhruvil.project.rideBooking.Ride.Booking.dto.RiderDto;
import com.dhruvil.project.rideBooking.Ride.Booking.entities.Driver;
import com.dhruvil.project.rideBooking.Ride.Booking.services.DriverService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class DriverServiceImpl implements DriverService {
    @Override
    public RideDto acceptRide(Long rideRequestId) {
        return null;
    }

    @Override
    public RideDto cancelRide(Long rideId) {
        return null;
    }

    @Override
    public RideDto startRide(Long rideId, String otp) {
        return null;
    }

    @Override
    public RideDto endRide(Long rideId) {
        return null;
    }

    @Override
    public RiderDto rateRider(Long rideId, Integer rating) {
        return null;
    }

    @Override
    public DriverDto getMyProfile() {
        return null;
    }

    @Override
    public Page<RideDto> getAllMyRides(PageRequest pageRequest) {
        return null;
    }

    @Override
    public Driver getCurrentDriver() {
        return null;
    }

    @Override
    public Driver updateDriverAvailability(Driver driver, boolean available) {
        return null;
    }

    @Override
    public Driver createNewDriver(Driver driver) {
        return null;
    }
}
