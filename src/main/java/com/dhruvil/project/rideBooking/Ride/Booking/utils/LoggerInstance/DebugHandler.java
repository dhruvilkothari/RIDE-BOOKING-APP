package com.dhruvil.project.rideBooking.Ride.Booking.utils.LoggerInstance;

class DebugHandler extends LogHandler {

    @Override
    public void handleLog(LogLevel level, String message) {
        if (level == LogLevel.DEBUG) {
            System.out.println("\033[0;32m" + message + "\033[0m"); // Green for DEBUG
        } else if (nextHandler != null) {
            nextHandler.handleLog(level, message);
        }
    }
}
