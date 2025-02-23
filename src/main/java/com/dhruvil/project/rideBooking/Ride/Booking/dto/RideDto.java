package com.dhruvil.project.rideBooking.Ride.Booking.dto;

import com.dhruvil.project.rideBooking.Ride.Booking.entities.enums.PaymentMethod;
import com.dhruvil.project.rideBooking.Ride.Booking.entities.enums.RideStatus;
import lombok.Data;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Data
public class RideDto {

    private Long id;
    private PointDto pickupLocation;
    private PointDto dropOffLocation;

    private LocalDateTime createdTime;
    private RiderDto rider;
    private DriverDto driver;
    private PaymentMethod paymentMethod;

    private RideStatus rideStatus;

    private String otp;

    private Double fare;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
}
