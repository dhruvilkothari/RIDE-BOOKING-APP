package com.dhruvil.project.rideBooking.Ride.Booking.repositories;


import com.dhruvil.project.rideBooking.Ride.Booking.entities.User;
import com.dhruvil.project.rideBooking.Ride.Booking.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByUser(User user);
}
