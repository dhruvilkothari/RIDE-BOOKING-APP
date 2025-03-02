package com.dhruvil.project.rideBooking.Ride.Booking.services.implementation;

import com.dhruvil.project.rideBooking.Ride.Booking.dto.DriverDto;
import com.dhruvil.project.rideBooking.Ride.Booking.dto.RideDto;
import com.dhruvil.project.rideBooking.Ride.Booking.dto.RideRequestDto;
import com.dhruvil.project.rideBooking.Ride.Booking.dto.RiderDto;
import com.dhruvil.project.rideBooking.Ride.Booking.entities.Driver;
import com.dhruvil.project.rideBooking.Ride.Booking.entities.RideRequest;
import com.dhruvil.project.rideBooking.Ride.Booking.entities.Rider;
import com.dhruvil.project.rideBooking.Ride.Booking.entities.User;
import com.dhruvil.project.rideBooking.Ride.Booking.entities.enums.RideRequestStatus;
import com.dhruvil.project.rideBooking.Ride.Booking.exceptions.ResourceNotFoundException;
import com.dhruvil.project.rideBooking.Ride.Booking.repositories.RideRequestRepository;
import com.dhruvil.project.rideBooking.Ride.Booking.repositories.RiderRepository;
import com.dhruvil.project.rideBooking.Ride.Booking.services.RiderService;
import com.dhruvil.project.rideBooking.Ride.Booking.stratergies.DriverMatchingStrategy;
import com.dhruvil.project.rideBooking.Ride.Booking.stratergies.RideFareCalculationStrategy;
import com.dhruvil.project.rideBooking.Ride.Booking.stratergies.RideStrategyManager;
import com.dhruvil.project.rideBooking.Ride.Booking.utils.Logger;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j

public class RiderServiceImpl implements RiderService {
    private final ModelMapper  modelMapper;
    private final RideStrategyManager rideStrategyManager;
    private final RideRequestRepository rideRequestRepository;
    private final RiderRepository riderRepository;
    @Override
    @Transactional
    public RideRequestDto requestRide(RideRequestDto rideRequestDto) {
        Rider rider = getCurrentRider();

        RideRequest rideRequest = modelMapper.map(rideRequestDto, RideRequest.class);
        rideRequest.setRideRequestStatus(RideRequestStatus.PENDING);
        rideRequest.setRider(rider);

//        Double fare = rideFareCalculationStrategy.calculateFare(rideRequest);
        Double fare = rideStrategyManager.rideFareCalculationStrategy().calculateFare(rideRequest);
        rideRequest.setFare(fare);

        RideRequest savedRideRequest = rideRequestRepository.save(rideRequest);

        List<Driver> drivers = rideStrategyManager.driverMatchingStrategy(rider.getRating()).findMatchingDriver(rideRequest);
        return modelMapper.map(savedRideRequest, RideRequestDto.class);
    }

    @Override
    public RideDto cancelRide(Long rideId) {
        return null;
    }

    @Override
    public DriverDto rateDriver(Long rideId, Integer rating) {
        return null;
    }

    @Override
    public RiderDto getMyProfile() {
        return null;
    }

    @Override
    public Page<RideDto> getAllMyRides(PageRequest pageRequest) {
        return null;
    }

    @Override
    public Rider createNewRider(User user) {
        Rider rider = Rider
                .builder()
                .user(user)
                .rating(0.0)
                .build();
        return riderRepository.save(rider);
    }

    @Override
    public Rider getCurrentRider() {
//        TODO : implement Spring security
        return riderRepository.findById(1L).orElseThrow(() -> new ResourceNotFoundException(
                "Rider not found with id: "+1
        ));
    }
}
