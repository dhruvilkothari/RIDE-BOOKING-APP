package com.dhruvil.project.rideBooking.Ride.Booking.repositories;

import com.dhruvil.project.rideBooking.Ride.Booking.entities.Driver;
import com.dhruvil.project.rideBooking.Ride.Booking.entities.Ride;
import com.dhruvil.project.rideBooking.Ride.Booking.entities.Rider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RideRepository extends JpaRepository<Ride,Long> {
    Page<Ride> findByDriver(Driver driver, PageRequest pageRequest);
    Page<Ride> findByRider(Rider rider, Pageable pageRequest);
}
