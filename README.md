# Ride Booking System: A Modern Spring Boot Application for Managing Ride Services

The Ride Booking System is a comprehensive Spring Boot application that provides a robust platform for managing ride-sharing services. This system enables riders to request rides, drivers to accept and manage rides, and handles all associated functionalities including payments, ratings, and user management.

The application implements a sophisticated service architecture with features including:
- User authentication and authorization with role-based access control
- Real-time ride matching using location-based services
- Multiple payment methods support (Wallet and Cash)
- Dynamic fare calculation with surge pricing
- Driver-rider rating system
- Wallet management with transaction tracking
- Distance calculation using OSRM (Open Source Routing Machine)
- Comprehensive error handling and response standardization

## Repository Structure
```
.
├── src/main/java/com/dhruvil/project/rideBooking/Ride/Booking/
│   ├── RideBookingApplication.java        # Main application entry point
│   ├── advices/                          # Global exception and response handlers
│   ├── configs/                          # Application configuration classes
│   ├── controller/                       # REST API endpoints
│   ├── dto/                             # Data Transfer Objects for API communication
│   ├── entities/                        # JPA entity classes and enums
│   ├── repositories/                    # Spring Data JPA repositories
│   ├── services/                        # Business logic interfaces and implementations
│   ├── stratergies/                     # Strategy pattern implementations for payments and matching
│   └── utils/                          # Utility classes for logging and calculations
├── resources/
│   ├── application.properties           # Application configuration properties
│   └── data.sql                        # Database initialization scripts
└── pom.xml                             # Maven project configuration
```

## Usage Instructions
### Prerequisites
- Java 21
- PostgreSQL database
- Maven 3.x
- OSRM service access for distance calculations

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

3. Build the application:
```bash
mvn clean install
```

4. Run the application:
```bash
mvn spring-boot:run
```

### Quick Start
1. Create a new user account:
```bash
curl -X POST http://localhost:8080/auth/signup \
  -H "Content-Type: application/json" \
  -d '{"name":"John Doe","email":"john@example.com","password":"password123"}'
```

2. Request a ride:
```bash
curl -X POST http://localhost:8080/rides/request \
  -H "Content-Type: application/json" \
  -d '{
    "pickupLocation": {"x": 73.856743, "y": 18.516726},
    "dropLocation": {"x": 73.856743, "y": 18.516726},
    "paymentMethod": "WALLET"
  }'
```

### More Detailed Examples
1. Driver onboarding:
```bash
curl -X POST http://localhost:8080/auth/driver/onboard \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 1,
    "vehicleId": "MH12AB1234"
  }'
```

2. Rate a completed ride:
```bash
curl -X POST http://localhost:8080/rides/{rideId}/rate \
  -H "Content-Type: application/json" \
  -d '{
    "rating": 5
  }'
```

### Troubleshooting
1. Database Connection Issues
- Error: "Could not connect to database"
- Solution: 
  ```bash
  # Check database status
  pg_isready -h localhost -p 5432
  # Verify database credentials in application.properties
  ```

2. OSRM Service Issues
- Error: "Error getting data from OSRM"
- Solution:
  - Verify OSRM service is accessible
  - Check network connectivity
  - Verify coordinates are within valid range

## Data Flow
The application follows a layered architecture for processing ride requests and managing bookings.

```ascii
[User/Driver] -> [Controllers] -> [Services] -> [Repositories]
     ↑              ↓               ↓              ↓
     └──────[Response]←──[DTOs]←──[Entities]←──[Database]
```

Component interactions:
1. Controllers receive HTTP requests and validate input
2. Services implement business logic and orchestrate operations
3. Strategies handle specific algorithms (matching, pricing)
4. Repositories manage data persistence
5. DTOs handle data transfer between layers
6. Global exception handlers manage error responses
7. Events trigger notifications and updates
8. Payment processing occurs asynchronously