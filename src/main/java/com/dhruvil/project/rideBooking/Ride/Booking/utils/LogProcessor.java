package com.dhruvil.project.rideBooking.Ride.Booking.utils;

public abstract class LogProcessor {
    public static int INFO = 1;
    public static int DEBUG = 2;
    public static int ERROR = 3;

    public LogProcessor nextLogProcessor ;
    public LogProcessor(LogProcessor logProcessor){

        this.nextLogProcessor = logProcessor;
    }
}
