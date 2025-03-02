package com.dhruvil.project.rideBooking.Ride.Booking.repositories;


import com.dhruvil.project.rideBooking.Ride.Booking.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
