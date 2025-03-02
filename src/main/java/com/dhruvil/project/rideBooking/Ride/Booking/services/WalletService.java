package com.dhruvil.project.rideBooking.Ride.Booking.services;

import com.dhruvil.project.rideBooking.Ride.Booking.entities.Ride;
import com.dhruvil.project.rideBooking.Ride.Booking.entities.User;
import com.dhruvil.project.rideBooking.Ride.Booking.entities.Wallet;
import com.dhruvil.project.rideBooking.Ride.Booking.entities.enums.TransactionMethod;
import org.springframework.stereotype.Service;


public interface WalletService {

    Wallet addMoneyToWallet(User user, Double amount,
                            String transactionId, Ride ride,
                            TransactionMethod transactionMethod);

    Wallet deductMoneyFromWallet(User user, Double amount,
                                 String transactionId, Ride ride,
                                 TransactionMethod transactionMethod);

    void withdrawAllMyMoneyFromWallet();

    Wallet findWalletById(Long walletId);

    Wallet createNewWallet(User user);

    Wallet findByUser(User user);

}
