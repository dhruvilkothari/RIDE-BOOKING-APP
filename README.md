# Ride Booking System: A Comprehensive Ride-Sharing Platform with Real-Time Matching and Secure Payments

A Spring Boot-based ride booking system that provides real-time driver matching, secure payment processing, and dynamic fare calculation with support for both cash and wallet payments.

The system implements advanced features like surge pricing, driver-rider rating system, and intelligent driver matching based on proximity and ratings. It provides a robust architecture for managing the complete lifecycle of ride bookings from request to payment processing, with built-in security using JWT authentication.

## Repository Structure
```
.
├── src/main/java/com/dhruvil/project/rideBooking/Ride/Booking/
│   ├── RideBookingApplication.java          # Main Spring Boot application entry point
│   ├── advices/                            # Global exception and response handlers
│   ├── configs/                            # Security and mapper configurations
│   ├── controller/                         # REST API endpoints for auth, drivers and riders
│   ├── dto/                               # Data transfer objects for API requests/responses
│   ├── entities/                          # JPA entities and enums for domain model
│   ├── repositories/                      # Spring Data JPA repositories
│   ├── security/                         # JWT authentication and security filters
│   ├── services/                         # Business logic implementation
│   │   └── impl/                        # Service implementations
│   ├── stratergies/                     # Strategy pattern implementations for core features
│   │   └── impl/                       # Concrete strategy implementations
│   └── utils/                          # Utility classes for logging and calculations
└── pom.xml                             # Maven project configuration
```

## Usage Instructions
### Prerequisites
- Java 21
- PostgreSQL database
- Maven
- Spring Boot 3.4.3

### Installation
1. Clone the repository:
```bash
git clone <repository-url>
cd ride-booking
```

2. Configure database properties in `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/ride_booking
spring.datasource.username=<your-username>
spring.datasource.password=<your-password>
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

2. Register a new rider:
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
  -d '{
    "pickupLocation": {"x": 12.34, "y": 56.78},
    "dropLocation": {"x": 23.45, "y": 67.89},
    "paymentMethod": "WALLET"
  }'
```

### More Detailed Examples
1. Driver Onboarding:
```bash
curl -X POST http://localhost:8080/auth/onboard-driver \
  -H "Authorization: Bearer <admin-token>" \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 123,
    "vehicleId": "KA01AB1234"
  }'
```

2. Rate a Driver:
```bash
curl -X POST http://localhost:8080/rider/rate-driver \
  -H "Authorization: Bearer <your-token>" \
  -H "Content-Type: application/json" \
  -d '{
    "rideId": 456,
    "rating": 5
  }'
```

### Troubleshooting
1. JWT Token Issues
- Problem: "Invalid JWT token" error
- Solution: 
```bash
# Check token expiration
curl -X POST http://localhost:8080/auth/refresh \
  -H "Cookie: refreshToken=<your-refresh-token>"
```

2. Driver Matching Issues
- Problem: No drivers found
- Solution: Enable debug logging in application.properties:
```properties
logging.level.com.dhruvil.project.rideBooking=DEBUG
```

## Data Flow
The system follows a request-response flow for ride booking with real-time driver matching.

```ascii
User -> AuthController -> JWT Authentication -> RiderController
  |
  v
RideRequest -> DriverMatchingStrategy -> Available Drivers
  |
  v
Driver Accepts -> RideService -> Payment Processing
  |
  v
Ride Completion -> Rating System -> Wallet Updates
```

Key Component Interactions:
1. Authentication flow uses JWT tokens for secure API access
2. RideStrategyManager determines driver matching and fare calculation strategies
3. PaymentStrategyManager handles different payment methods (CASH/WALLET)
4. Rating system updates both driver and rider ratings after ride completion
5. Wallet transactions are processed asynchronously for payment settlements
6. Email notifications are sent for ride status updates
7. Geographic calculations use OSRM for distance and route optimization