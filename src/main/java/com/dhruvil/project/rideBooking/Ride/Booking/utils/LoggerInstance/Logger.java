package com.dhruvil.project.rideBooking.Ride.Booking.utils.LoggerInstance;

public class Logger {
    private static Logger instance;
    private LogHandler logHandler;

    // Private constructor for Singleton pattern
    private Logger() {
        // Chain of Responsibility initialization
        LogHandler infoHandler = new InfoHandler();
        LogHandler debugHandler = new DebugHandler();
        LogHandler errorHandler = new ErrorHandler();

        // Linking the handlers in the chain
        infoHandler.setNextHandler(debugHandler);
        debugHandler.setNextHandler(errorHandler);

        logHandler = infoHandler; // Start with infoHandler in the chain
    }

    // Method to get the Singleton instance
    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    // Method to log messages
    public void log(LogLevel level, String message) {
        logHandler.handleLog(level, message);
    }
}
