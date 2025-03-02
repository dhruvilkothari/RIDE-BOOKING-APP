package com.dhruvil.project.rideBooking.Ride.Booking.stratergies;

import com.codingshuttle.project.uber.uberApp.entities.Payment;

public interface PaymentStrategy {
    Double PLATFORM_COMMISSION = 0.3;
    void processPayment(Payment payment);

}
