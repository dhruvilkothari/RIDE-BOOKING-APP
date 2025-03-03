package com.dhruvil.project.rideBooking.Ride.Booking.repositories;

import com.codingshuttle.project.uber.uberApp.entities.Rider;
import com.codingshuttle.project.uber.uberApp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface RiderRepository extends JpaRepository<Rider, Long> {
    Optional<Rider> findByUser(User user);
}
