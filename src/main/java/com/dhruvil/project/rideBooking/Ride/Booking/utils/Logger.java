package com.dhruvil.project.rideBooking.Ride.Booking.utils;

import com.dhruvil.project.rideBooking.Ride.Booking.consts.Color;

public class Logger {
    public static volatile Logger logger;

    private Logger(){
    }
    public Logger getInstance(){
        if(logger == null){
            synchronized (Logger.class){
                if(logger == null){
                    logger = new Logger();
                }
            }
        }
        return logger;
    }
    public static void log(String message) {
        System.out.println(Color.RED+message+Color.RESET);
    }
}
