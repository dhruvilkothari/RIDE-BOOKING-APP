package com.dhruvil.project.rideBooking.Ride.Booking.services.implementation;

import com.dhruvil.project.rideBooking.Ride.Booking.dto.DriverDto;
import com.dhruvil.project.rideBooking.Ride.Booking.dto.SignupDto;
import com.dhruvil.project.rideBooking.Ride.Booking.dto.UserDto;
import com.dhruvil.project.rideBooking.Ride.Booking.entities.User;
import com.dhruvil.project.rideBooking.Ride.Booking.entities.enums.Role;
import com.dhruvil.project.rideBooking.Ride.Booking.exceptions.RuntimeConflictException;
import com.dhruvil.project.rideBooking.Ride.Booking.repositories.UserRepository;
import com.dhruvil.project.rideBooking.Ride.Booking.services.AuthService;
import com.dhruvil.project.rideBooking.Ride.Booking.services.RiderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final ModelMapper  modelMapper;
    private final UserRepository  userRepository;
    private final RiderService riderService;
    @Override
    public String[] login(String email, String password) {
        return new String[0];
    }

    @Override
    @Transactional
    public UserDto signup(SignupDto signupDto) {
        User user = userRepository.findByEmail(signupDto.getEmail()).orElse(null);
        if(user != null)
            throw new RuntimeConflictException("Cannot signup, User already exists with email "+signupDto.getEmail());
        User mappedUser = modelMapper.map(signupDto, User.class);
        mappedUser.setRoles(Set.of(Role.RIDER));
        User savedUser = userRepository.save(mappedUser);

//        create user related entities
        riderService.createNewRider(savedUser);
//        TODO add wallet related service here

        return modelMapper.map(savedUser, UserDto.class);


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
