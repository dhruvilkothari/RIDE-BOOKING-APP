package com.dhruvil.project.rideBooking.Ride.Booking.services;

import org.locationtech.jts.geom.Point;

public interface DistanceService {
    double calculateDistance(Point src,Point dest);
}
