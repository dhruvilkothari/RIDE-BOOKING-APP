package com.dhruvil.project.rideBooking.Ride.Booking.utils.LoggerInstance;

class InfoHandler extends LogHandler {

    @Override
    public void handleLog(LogLevel level, String message) {
        if (level == LogLevel.INFO) {
            System.out.println("\033[0;33m" + message + "\033[0m"); // Yellow for INFO
        } else if (nextHandler != null) {
            nextHandler.handleLog(level, message);
        }
    }
}