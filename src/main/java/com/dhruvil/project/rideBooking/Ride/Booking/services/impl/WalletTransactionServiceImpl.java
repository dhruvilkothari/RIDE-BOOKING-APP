package com.dhruvil.project.rideBooking.Ride.Booking.services.impl;

import com.dhruvil.project.rideBooking.Ride.Booking.entities.WalletTransaction;
import com.dhruvil.project.rideBooking.Ride.Booking.repositories.WalletTransactionRepository;
import com.dhruvil.project.rideBooking.Ride.Booking.services.WalletTransactionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletTransactionServiceImpl implements WalletTransactionService {

    private final WalletTransactionRepository walletTransactionRepository;
    private final ModelMapper modelMapper;

    @Override
    public void createNewWalletTransaction(WalletTransaction walletTransaction) {
        walletTransactionRepository.save(walletTransaction);
    }

}
