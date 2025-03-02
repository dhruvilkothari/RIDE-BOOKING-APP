# Ride Booking System: A Comprehensive Ride-Sharing Platform with Secure Payments and Real-Time Matching

A Spring Boot-based ride-sharing application that provides seamless ride booking, real-time driver matching, secure payments, and rating systems. The platform offers both riders and drivers a robust experience with features like surge pricing, multiple payment methods, and comprehensive transaction management.

The system implements advanced features including secure JWT authentication, wallet-based payments, driver-rider matching algorithms, and a sophisticated logging system. It provides real-time distance calculations using OSRM (Open Source Routing Machine) and handles complex payment flows with support for both cash and digital wallet transactions.

## Repository Structure
```
src/
├── main/java/com/dhruvil/project/rideBooking/Ride/Booking/  # Core application code
│   ├── RideBookingApplication.java                          # Application entry point
│   ├── advices/                                            # Global exception and response handlers
│   ├── configs/                                            # Security and mapper configurations
│   ├── controller/                                         # REST API endpoints for auth, drivers, and riders
│   ├── dto/                                               # Data transfer objects for API communication
│   ├── entities/                                          # JPA entities and enums
│   ├── repositories/                                      # Spring Data JPA repositories
│   ├── security/                                         # JWT authentication and security config
│   ├── services/                                         # Business logic implementation
│   ├── stratergies/                                      # Strategy patterns for payments and matching
│   └── utils/                                            # Utility classes and logging framework
└── test/                                                 # Test cases and configurations
```

## Design Patterns

The application implements several design patterns to ensure maintainable, extensible, and robust code:

1. **Strategy Pattern**
   - Payment Processing: `PaymentStrategy` interface with implementations for different payment methods
   - Driver Matching: `DriverMatchingStrategy` for different driver selection algorithms
   - Ride Fare Calculation: `RideFareCalculationStrategy` for dynamic pricing models

2. **Chain of Responsibility**
   - Logging System: Implements a chain of log handlers (InfoHandler → DebugHandler → ErrorHandler)
   - Each handler processes logs of specific levels and passes others to the next handler
   ```java
   LogHandler infoHandler = new InfoHandler();
   LogHandler debugHandler = new DebugHandler();
   LogHandler errorHandler = new ErrorHandler();
   ```

3. **Singleton Pattern**
   - Logger Implementation: Ensures a single logging instance throughout the application
   ```java
   public class Logger {
       private static Logger instance;
       public static Logger getInstance() {
           if (instance == null) {
               instance = new Logger();
           }
           return instance;
       }
   }
   ```

4. **Builder Pattern**
   - DTO Construction: Used in WalletTransactionDto, ApiError, and other DTOs
   - Entity Creation: Simplifies complex object creation with many optional parameters

5. **Repository Pattern**
   - Data Access Layer: Implemented through Spring Data JPA repositories
   - Provides clean separation between business logic and data access

6. **Factory Pattern**
   - Payment Strategy Creation: `PaymentStrategyManager` creates appropriate payment strategies
   - Ride Strategy Management: `RideStrategyManager` handles different ride-related strategies

7. **Dependency Injection**
   - Spring Framework's IoC container manages dependencies
   - Promotes loose coupling and easier testing
   ```java
   @Service
   @RequiredArgsConstructor
   public class PaymentServiceImpl implements PaymentService {
       private final PaymentRepository paymentRepository;
       private final PaymentStrategyManager paymentStrategyManager;
   }
   ```

## Usage Instructions
### Prerequisites
- Java 21
- Maven
- PostgreSQL database
- OSRM server for distance calculations

### Installation
1. Clone the repository:
```bash
git clone <repository-url>
cd ride-booking
```

2. Configure database properties in `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/ride_booking
spring.datasource.username=your_username
spring.datasource.password=your_password
```

3. Build the project:
```bash
mvn clean install
```

### Quick Start
1. Start the application:
```bash
mvn spring-boot:run
```

2. Register a new user:
```bash
curl -X POST http://localhost:8080/auth/signup \
  -H "Content-Type: application/json" \
  -d '{"name":"John Doe","email":"john@example.com","password":"password123"}'
```

3. Request a ride:
```bash
curl -X POST http://localhost:8080/rider/request-ride \
  -H "Authorization: Bearer <your-token>" \
  -H "Content-Type: application/json" \
  -d '{"source":{"x":12.34,"y":56.78},"destination":{"x":90.12,"y":34.56},"paymentMethod":"WALLET"}'
```

### More Detailed Examples
1. Driver accepting a ride:
```bash
curl -X POST http://localhost:8080/driver/accept-ride/{rideRequestId} \
  -H "Authorization: Bearer <driver-token>"
```

2. Starting a ride with OTP:
```bash
curl -X POST http://localhost:8080/driver/start-ride/{rideId} \
  -H "Authorization: Bearer <driver-token>" \
  -d '{"otp":"1234"}'
```

### Troubleshooting
1. JWT Authentication Issues
   - Error: "Invalid token"
   - Solution: Ensure token is not expired and properly formatted
   - Debug: Enable debug logging in security config
```properties
logging.level.org.springframework.security=DEBUG
```

2. Payment Processing Failures
   - Error: "Insufficient wallet balance"
   - Check wallet balance using:
```bash
curl -X GET http://localhost:8080/rider/wallet \
  -H "Authorization: Bearer <your-token>"
```

## Data Flow
The application follows a request-response flow for ride booking and management.

```ascii
User Request -> Authentication -> Business Logic -> Database
     ↑                              ↓
     └──────── Response ←──── Event Processing
```

Key component interactions:
1. Authentication flow using JWT tokens
2. Driver matching using location-based algorithms
3. Payment processing through wallet or cash
4. Rating system for both drivers and riders
5. Real-time distance calculation using OSRM
6. Transaction management with wallet system
7. Event-driven status updates for rides

Design Patterns
1. Singleton Design pattern for Logger
2. Chain of Responsibility for different logging levels
3. Stratergy design pattern for Payment, matching driver and fare calculation.
4. Builder design pattern for creating different Objects.