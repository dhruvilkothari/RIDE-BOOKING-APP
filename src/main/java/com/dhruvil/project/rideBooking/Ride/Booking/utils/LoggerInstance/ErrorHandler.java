package com.dhruvil.project.rideBooking.Ride.Booking.utils.LoggerInstance;

class ErrorHandler extends LogHandler {

    @Override
    public void handleLog(LogLevel level, String message) {
        if (level == LogLevel.ERROR) {
            System.out.println("\033[0;31m" + message + "\033[0m"); // Red for ERROR
        } else if (nextHandler != null) {
            nextHandler.handleLog(level, message);
        }
    }
}