package com.dhruvil.project.rideBooking.Ride.Booking.services.implementation;

import com.dhruvil.project.rideBooking.Ride.Booking.dto.DriverDto;
import com.dhruvil.project.rideBooking.Ride.Booking.dto.SignupDto;
import com.dhruvil.project.rideBooking.Ride.Booking.dto.UserDto;
import com.dhruvil.project.rideBooking.Ride.Booking.services.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Override
    public String[] login(String email, String password) {
        return new String[0];
    }

    @Override
    public UserDto signup(SignupDto signupDto) {
        return null;
    }

    @Override
    public DriverDto onboardNewDriver(Long userId, String vehicleId) {
        return null;
    }

    @Override
    public String refreshToken(String refreshToken) {
        return "";
    }
}
