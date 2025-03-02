package com.dhruvil.project.rideBooking.Ride.Booking.utils.LoggerInstance;

abstract class LogHandler {
    protected LogHandler nextHandler;

    public void setNextHandler(LogHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public abstract void handleLog(LogLevel level, String message);
}