package com.dhruvil.project.rideBooking.Ride.Booking.stratergies;

import com.dhruvil.project.rideBooking.Ride.Booking.entities.enums.PaymentMethod;
import com.dhruvil.project.rideBooking.Ride.Booking.stratergies.impl.CashPaymentStrategy;
import com.dhruvil.project.rideBooking.Ride.Booking.stratergies.impl.WalletPaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentStrategyManager {

    private final WalletPaymentStrategy walletPaymentStrategy;
    private final CashPaymentStrategy cashPaymentStrategy;

    public PaymentStrategy paymentStrategy(PaymentMethod paymentMethod) {
        return switch (paymentMethod) {
            case WALLET -> walletPaymentStrategy;
            case CASH -> cashPaymentStrategy;
        };
    }
}
