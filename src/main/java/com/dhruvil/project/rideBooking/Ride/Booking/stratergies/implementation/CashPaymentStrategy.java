package com.dhruvil.project.rideBooking.Ride.Booking.stratergies.implementation;


import com.dhruvil.project.rideBooking.Ride.Booking.entities.Driver;
import com.dhruvil.project.rideBooking.Ride.Booking.entities.Payment;
import com.dhruvil.project.rideBooking.Ride.Booking.entities.enums.PaymentStatus;
import com.dhruvil.project.rideBooking.Ride.Booking.entities.enums.TransactionMethod;
import com.dhruvil.project.rideBooking.Ride.Booking.repositories.PaymentRepository;
import com.dhruvil.project.rideBooking.Ride.Booking.services.WalletService;
import com.dhruvil.project.rideBooking.Ride.Booking.stratergies.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

//Rider -> 100
//Driver -> 70 Deduct 30Rs from Driver's wallet

@Service
@RequiredArgsConstructor
public class CashPaymentStrategy implements PaymentStrategy {

    private final WalletService walletService;
    private final PaymentRepository paymentRepository;

    @Override
    public void processPayment(Payment payment) {
        Driver driver = payment.getRide().getDriver();

        double platformCommission = payment.getAmount() * PLATFORM_COMMISSION;

        walletService.deductMoneyFromWallet(driver.getUser(), platformCommission, null,
                payment.getRide(), TransactionMethod.RIDE);

        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepository.save(payment);
    }
}

//10 ratingsCount -> 4.0
//new rating 4.6
//updated rating
//new rating 44.6/11 -> 4.05