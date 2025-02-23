package com.dhruvil.project.rideBooking.Ride.Booking.services;


import com.dhruvil.project.rideBooking.Ride.Booking.entities.Payment;
import com.dhruvil.project.rideBooking.Ride.Booking.entities.Ride;
import com.dhruvil.project.rideBooking.Ride.Booking.entities.enums.PaymentStatus;

public interface PaymentService {

    void processPayment(Ride ride);

    Payment createNewPayment(Ride ride);

    void updatePaymentStatus(Payment payment, PaymentStatus status);
}
