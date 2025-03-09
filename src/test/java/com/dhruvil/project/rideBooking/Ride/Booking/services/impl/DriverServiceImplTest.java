
import com.dhruvil.project.rideBooking.Ride.Booking.dto.DriverDto;
import com.dhruvil.project.rideBooking.Ride.Booking.dto.RideDto;
import com.dhruvil.project.rideBooking.Ride.Booking.dto.RiderDto;
import com.dhruvil.project.rideBooking.Ride.Booking.entities.Driver;
import com.dhruvil.project.rideBooking.Ride.Booking.entities.Ride;
import com.dhruvil.project.rideBooking.Ride.Booking.entities.RideRequest;
import com.dhruvil.project.rideBooking.Ride.Booking.entities.User;
//import com.dhruvil.project.rideBooking.Ride.Booking.entities.enums.RideRequestStatus;
import com.dhruvil.project.rideBooking.Ride.Booking.entities.enums.RideStatus;
import com.dhruvil.project.rideBooking.Ride.Booking.repositories.DriverRepository;
import com.dhruvil.project.rideBooking.Ride.Booking.services.PaymentService;
import com.dhruvil.project.rideBooking.Ride.Booking.services.RatingService;
import com.dhruvil.project.rideBooking.Ride.Booking.services.RideRequestService;
import com.dhruvil.project.rideBooking.Ride.Booking.services.RideService;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.dhruvil.project.rideBooking.Ride.Booking.services.impl.DriverServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
//No pre-existing test file

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class DriverServiceImplTest {

    @Mock
    private Authentication authentication;

    @Mock
    private DriverRepository driverRepository;

    @InjectMocks
    private DriverServiceImpl driverService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PaymentService paymentService;

    @Mock
    private RatingService ratingService;

    @Mock
    private RideRequestService rideRequestService;

    @Mock
    private RideService rideService;

    @Mock
    private SecurityContext securityContext;

    /**
     * Test case for acceptRide method when ride request status is not PENDING and driver is not available.
     * This test verifies that a RuntimeException is thrown when attempting to accept a ride
     * with an invalid ride request status and an unavailable driver.
     */
    @Test
    public void testAcceptRideWithInvalidStatusAndUnavailableDriver() {
        // Arrange
        Long rideRequestId = 1L;
        RideRequest rideRequest = new RideRequest();
        rideRequest.setRideRequestStatus(RideRequestStatus.ACCEPTED);

        Driver driver = new Driver();
        driver.setAvailable(false);

        when(rideRequestService.findRideRequestById(rideRequestId)).thenReturn(rideRequest);
        when(driverService.getCurrentDriver()).thenReturn(driver);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> driverService.acceptRide(rideRequestId));
    }

    /**
     * Test that acceptRide throws a RuntimeException when the ride request status is not PENDING.
     */
    @Test
    public void testAcceptRide_NonPendingRideRequest_ThrowsException() {
        Long rideRequestId = 1L;
        RideRequest rideRequest = new RideRequest();
        rideRequest.setRideRequestStatus(RideRequestStatus.ACCEPTED);

        when(rideRequestService.findRideRequestById(rideRequestId)).thenReturn(rideRequest);

        assertThrows(RuntimeException.class, () -> driverService.acceptRide(rideRequestId));
    }

    /**
     * Test that acceptRide throws a RuntimeException when the driver is not available.
     */
    @Test
    public void testAcceptRide_UnavailableDriver_ThrowsException() {
        Long rideRequestId = 1L;
        RideRequest rideRequest = new RideRequest();
        rideRequest.setRideRequestStatus(RideRequestStatus.PENDING);

        Driver unavailableDriver = new Driver();
        unavailableDriver.setAvailable(false);

        when(rideRequestService.findRideRequestById(rideRequestId)).thenReturn(rideRequest);
        when(driverService.getCurrentDriver()).thenReturn(unavailableDriver);

        assertThrows(RuntimeException.class, () -> driverService.acceptRide(rideRequestId));
    }

    /**
     * Test case for acceptRide method when the ride request status is not PENDING
     * and the current driver is available.
     * 
     * This test verifies that a RuntimeException is thrown when attempting to accept
     * a ride with a non-PENDING status, even if the driver is available.
     */
    @Test
    public void test_acceptRide_3() {
        // Arrange
        Long rideRequestId = 1L;
        RideRequest rideRequest = new RideRequest();
        rideRequest.setRideRequestStatus(RideRequestStatus.ACCEPTED);

        Driver currentDriver = new Driver();
        currentDriver.setAvailable(true);

        when(rideRequestService.findRideRequestById(rideRequestId)).thenReturn(rideRequest);
        when(driverService.getCurrentDriver()).thenReturn(currentDriver);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> driverService.acceptRide(rideRequestId));
    }

    /**
     * Test case for acceptRide method when the ride request is pending but the driver is unavailable.
     * This test verifies that a RuntimeException is thrown when a driver attempts to accept a ride
     * while they are not available.
     */
    @Test
    public void test_acceptRide_whenRideRequestPendingButDriverUnavailable() {
        // Arrange
        Long rideRequestId = 1L;
        RideRequest rideRequest = new RideRequest();
        rideRequest.setRideRequestStatus(RideRequestStatus.PENDING);

        Driver currentDriver = new Driver();
        currentDriver.setAvailable(false);

        when(rideRequestService.findRideRequestById(rideRequestId)).thenReturn(rideRequest);
        when(driverService.getCurrentDriver()).thenReturn(currentDriver);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> driverService.acceptRide(rideRequestId));

        // Verify
        verify(rideRequestService).findRideRequestById(rideRequestId);
        verify(driverService).getCurrentDriver();
        verifyNoMoreInteractions(rideService, modelMapper);
    }

    /**
     * Test case for cancelling a ride with a driver who did not accept the ride.
     * This test verifies that the cancelRide method throws a RuntimeException
     * when the current driver is not the one who accepted the ride.
     */
    @Test
    public void test_cancelRide_driverNotAccepted() {
        Long rideId = 1L;
        Ride ride = new Ride();
        Driver currentDriver = new Driver();
        Driver rideDriver = new Driver();

        ride.setDriver(rideDriver);
        ride.setRideStatus(RideStatus.CONFIRMED);

        when(rideService.getRideById(rideId)).thenReturn(ride);
        when(driverService.getCurrentDriver()).thenReturn(currentDriver);

        assertThrows(RuntimeException.class, () -> driverService.cancelRide(rideId));
    }

    /**
     * Test case for cancelRide method when the driver is not the owner of the ride
     * and the ride status is not CONFIRMED.
     * Expected: RuntimeException is thrown
     */
    @Test
    public void test_cancelRide_driverNotOwnerAndInvalidStatus() {
        Long rideId = 1L;
        Ride ride = new Ride();
        ride.setRideStatus(RideStatus.ONGOING);

        Driver currentDriver = new Driver();
        Driver rideDriver = new Driver();
        ride.setDriver(rideDriver);

        when(rideService.getRideById(rideId)).thenReturn(ride);
        when(driverService.getCurrentDriver()).thenReturn(currentDriver);

        assertThrows(RuntimeException.class, () -> driverService.cancelRide(rideId));
    }

    /**
     * Test case for cancelling a ride with an invalid ride status.
     * This test verifies that the cancelRide method throws a RuntimeException
     * when the ride status is not CONFIRMED.
     */
    @Test
    public void test_cancelRide_invalidRideStatus() {
        Long rideId = 1L;
        Ride ride = new Ride();
        Driver driver = new Driver();

        ride.setDriver(driver);
        ride.setRideStatus(RideStatus.ONGOING);

        when(rideService.getRideById(rideId)).thenReturn(ride);
        when(driverService.getCurrentDriver()).thenReturn(driver);

        assertThrows(RuntimeException.class, () -> driverService.cancelRide(rideId));
    }

    /**
     * Test case for canceling a ride when the driver matches the ride's driver and the ride status is CONFIRMED.
     * This test verifies that the cancelRide method successfully cancels the ride and returns the expected RideDto.
     */
    @Test
    public void test_cancelRide_whenDriverMatchesAndRideStatusIsConfirmed() {
        // Arrange
        Long rideId = 1L;
        Ride ride = new Ride();
        ride.setRideStatus(RideStatus.CONFIRMED);
        Driver driver = new Driver();
        ride.setDriver(driver);
        RideDto expectedRideDto = new RideDto();

        when(rideService.getRideById(rideId)).thenReturn(ride);
        when(driverService.getCurrentDriver()).thenReturn(driver);
        when(rideService.updateRideStatus(ride, RideStatus.CANCELLED)).thenReturn(ride);
        when(modelMapper.map(ride, RideDto.class)).thenReturn(expectedRideDto);

        // Act
        RideDto result = driverService.cancelRide(rideId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedRideDto, result);
        verify(rideService).updateRideStatus(ride, RideStatus.CANCELLED);
        verify(driverService).updateDriverAvailability(driver, true);
    }

    /**
     * Test case for cancelRide method when the ride status is not CONFIRMED.
     * This test verifies that a RuntimeException is thrown when attempting to cancel a ride
     * that is not in the CONFIRMED status.
     */
    @Test
    public void test_cancelRide_whenRideStatusIsNotConfirmed() {
        // Arrange
        Long rideId = 1L;
        Ride ride = new Ride();
        ride.setRideStatus(RideStatus.ONGOING);
        Driver driver = new Driver();

        when(rideService.getRideById(rideId)).thenReturn(ride);
        when(driverService.getCurrentDriver()).thenReturn(driver);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> driverService.cancelRide(rideId));

        // Verify
        verify(rideService).getRideById(rideId);
        verify(driverService).getCurrentDriver();
        verifyNoMoreInteractions(rideService, modelMapper);
    }

    /**
     * Tests that the createNewDriver method correctly saves and returns a Driver object.
     * This test verifies that the method simply delegates to the repository's save method
     * and returns its result without any additional processing or validation.
     */
    @Test
    public void test_createNewDriver_savesAndReturnsDriver() {
        Driver inputDriver = new Driver();
        Driver savedDriver = new Driver();
        Mockito.when(driverRepository.save(any(Driver.class))).thenReturn(savedDriver);

        Driver result = driverService.createNewDriver(inputDriver);

        assertEquals(savedDriver, result);
        Mockito.verify(driverRepository).save(inputDriver);
    }

    /**
     * Test case for createNewDriver method
     * Verifies that the method correctly saves a new driver using the repository
     */
    @Test
    public void test_createNewDriver_savesDriverSuccessfully() {
        // Arrange
        Driver driver = new Driver();
        Mockito.when(driverRepository.save(driver)).thenReturn(driver);

        // Act
        Driver result = driverService.createNewDriver(driver);

        // Assert
        Mockito.verify(driverRepository).save(driver);
        assert result == driver;
    }

    /**
     * Test case for endRide method when the driver is not the owner of the ride
     * and the ride status is not ONGOING.
     * 
     * Expected: RuntimeException is thrown with appropriate error message.
     */
    @Test
    public void test_endRideWithInvalidDriverAndStatus() {
        // Arrange
        Long rideId = 1L;
        Ride ride = new Ride();
        ride.setDriver(new Driver());
        ride.setRideStatus(RideStatus.CONFIRMED);

        Driver currentDriver = new Driver();

        when(rideService.getRideById(rideId)).thenReturn(ride);
        when(driverService.getCurrentDriver()).thenReturn(currentDriver);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            driverService.endRide(rideId);
        });

        assertEquals("Driver cannot start a ride as he has not accepted it earlier", exception.getMessage());

        verify(rideService).getRideById(rideId);
        verify(driverService).getCurrentDriver();
        verifyNoMoreInteractions(rideService, paymentService, modelMapper);
    }

    /**
     * Tests the endRide method when the driver attempting to end the ride
     * is not the same as the driver associated with the ride.
     * Expects a RuntimeException to be thrown.
     */
    @Test
    public void test_endRide_driverMismatch() {
        Long rideId = 1L;
        Ride ride = new Ride();
        Driver rideDriver = new Driver();
        ride.setDriver(rideDriver);

        Driver currentDriver = new Driver();

        when(rideService.getRideById(rideId)).thenReturn(ride);
        when(driverService.getCurrentDriver()).thenReturn(currentDriver);

        assertThrows(RuntimeException.class, () -> driverService.endRide(rideId));
    }

    /**
     * Tests the endRide method when the ride status is not ONGOING.
     * Expects a RuntimeException to be thrown.
     */
    @Test
    public void test_endRide_invalidRideStatus() {
        Long rideId = 1L;
        Ride ride = new Ride();
        Driver driver = new Driver();
        ride.setDriver(driver);
        ride.setRideStatus(RideStatus.CONFIRMED);

        when(rideService.getRideById(rideId)).thenReturn(ride);
        when(driverService.getCurrentDriver()).thenReturn(driver);

        assertThrows(RuntimeException.class, () -> driverService.endRide(rideId));
    }

    /**
     * Test case for endRide method when the ride is ongoing and the driver is valid.
     * This test verifies that the ride is successfully ended, driver availability is updated,
     * payment is processed, and the correct RideDto is returned.
     */
    @Test
    public void test_endRide_whenRideOngoingAndDriverValid() {
        // Arrange
        Long rideId = 1L;
        Ride ride = new Ride();
        ride.setRideStatus(RideStatus.ONGOING);
        Driver driver = new Driver();
        ride.setDriver(driver);
        RideDto expectedRideDto = new RideDto();

        when(rideService.getRideById(rideId)).thenReturn(ride);
        when(driverService.getCurrentDriver()).thenReturn(driver);
        when(rideService.updateRideStatus(any(Ride.class), eq(RideStatus.ENDED))).thenReturn(ride);
        when(modelMapper.map(ride, RideDto.class)).thenReturn(expectedRideDto);

        // Act
        RideDto result = driverService.endRide(rideId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedRideDto, result);
        verify(rideService).updateRideStatus(any(Ride.class), eq(RideStatus.ENDED));
        verify(paymentService).processPayment(ride);
    }

    /**
     * Tests the getAllMyRides method when the driver has no rides.
     * This is an edge case where the result should be an empty page.
     */
    @Test
    public void test_getAllMyRides_noRides() {
        Driver currentDriver = new Driver();
        PageRequest pageRequest = PageRequest.of(0, 10);

        when(driverService.getCurrentDriver()).thenReturn(currentDriver);
        when(rideService.getAllRidesOfDriver(currentDriver, pageRequest))
                .thenReturn(new PageImpl<>(Collections.emptyList()));

        Page<RideDto> result = driverService.getAllMyRides(pageRequest);

        assertTrue(result.isEmpty());
    }

    /**
     * Test case for getAllMyRides method.
     * It verifies that the method correctly retrieves and maps rides for the current driver.
     */
    @Test
    public void test_getAllMyRides_returnsCorrectlyMappedRides() {
        // Arrange
        Driver currentDriver = new Driver();
        PageRequest pageRequest = PageRequest.of(0, 10);
        List<Ride> rides = Arrays.asList(new Ride(), new Ride());
        Page<Ride> ridePage = new PageImpl<>(rides);

        when(driverService.getCurrentDriver()).thenReturn(currentDriver);
        when(rideService.getAllRidesOfDriver(currentDriver, pageRequest)).thenReturn(ridePage);
        when(modelMapper.map(rides.get(0), RideDto.class)).thenReturn(new RideDto());
        when(modelMapper.map(rides.get(1), RideDto.class)).thenReturn(new RideDto());

        // Act
        Page<RideDto> result = driverService.getAllMyRides(pageRequest);

        // Assert
        assertEquals(2, result.getContent().size());
        assertEquals(RideDto.class, result.getContent().get(0).getClass());
        assertEquals(RideDto.class, result.getContent().get(1).getClass());
    }

    /**
     * Test case for getCurrentDriver method when no driver is associated with the authenticated user.
     * This test verifies that a ResourceNotFoundException is thrown when the driver is not found.
     */
    @Test
    public void test_getCurrentDriver_driverNotFound() {
        User user = new User();
        user.setId(1L);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(user);
        when(driverRepository.findByUser(user)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> driverService.getCurrentDriver());
    }

    /**
     * Test case for getCurrentDriver method when a driver is associated with the authenticated user.
     * This test verifies that the method returns the correct driver when one is found in the repository.
     */
    @Test
    public void test_getCurrentDriver_whenDriverExists() {
        User user = new User();
        user.setId(1L);
        Driver expectedDriver = new Driver();
        expectedDriver.setUser(user);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(user);
        when(driverRepository.findByUser(user)).thenReturn(Optional.of(expectedDriver));

        Driver actualDriver = driverService.getCurrentDriver();

        assertEquals(expectedDriver, actualDriver);
    }

    /**
     * Test case for getMyProfile method
     * Verifies that the method correctly maps the current driver to a DriverDto
     */
    @Test
    public void test_getMyProfile_returnsCorrectDriverDto() {
        // Arrange
        Driver mockDriver = new Driver();
        DriverDto mockDriverDto = new DriverDto();

        when(driverService.getCurrentDriver()).thenReturn(mockDriver);
        when(modelMapper.map(mockDriver, DriverDto.class)).thenReturn(mockDriverDto);

        // Act
        DriverDto result = driverService.getMyProfile();

        // Assert
        assertNotNull(result);
        assertEquals(mockDriverDto, result);
        verify(driverService).getCurrentDriver();
        verify(modelMapper).map(mockDriver, DriverDto.class);
    }

    /**
     * Test case for rateRider method when the driver is not the owner of the ride.
     * This test verifies that a RuntimeException is thrown when a driver attempts to rate a ride they didn't drive.
     */
    @Test
    public void test_rateRider_driverNotOwner() {
        Long rideId = 1L;
        Integer rating = 5;
        Ride ride = new Ride();
        Driver currentDriver = new Driver();
        Driver rideDriver = new Driver();
        ride.setDriver(rideDriver);

        when(rideService.getRideById(rideId)).thenReturn(ride);
        when(driverService.getCurrentDriver()).thenReturn(currentDriver);

        assertThrows(RuntimeException.class, () -> driverService.rateRider(rideId, rating),
                "Driver is not the owner of this Ride");
    }

    /**
     * Test case for rateRider method when the driver is not the owner of the ride
     * and the ride status is not ENDED.
     */
    @Test
    public void test_rateRider_driverNotOwnerAndRideNotEnded() {
        Long rideId = 1L;
        Integer rating = 5;

        Driver mockDriver = new Driver();
        Ride mockRide = new Ride();
        mockRide.setDriver(new Driver()); // Set a different driver
        mockRide.setRideStatus(RideStatus.ONGOING);

        when(rideService.getRideById(rideId)).thenReturn(mockRide);
        when(driverService.getCurrentDriver()).thenReturn(mockDriver);

        assertThrows(RuntimeException.class, () -> driverService.rateRider(rideId, rating));

        verify(rideService).getRideById(rideId);
        verify(driverService).getCurrentDriver();
        verifyNoInteractions(ratingService);
    }

    /**
     * Test case for rateRider method when the ride status is not ENDED.
     * This test verifies that a RuntimeException is thrown when attempting to rate a ride that has not ended.
     */
    @Test
    public void test_rateRider_rideNotEnded() {
        Long rideId = 1L;
        Integer rating = 5;
        Ride ride = new Ride();
        Driver driver = new Driver();
        ride.setDriver(driver);
        ride.setRideStatus(RideStatus.ONGOING);

        when(rideService.getRideById(rideId)).thenReturn(ride);
        when(driverService.getCurrentDriver()).thenReturn(driver);

        assertThrows(RuntimeException.class, () -> driverService.rateRider(rideId, rating),
                "Ride status is not Ended hence cannot start rating, status: ONGOING");
    }

    /**
     * Test case for rateRider method when the driver is the owner of the ride and the ride status is ENDED.
     * This test verifies that the rateRider method correctly delegates to the ratingService
     * when all conditions are met.
     */
    @Test
    public void test_rateRider_whenDriverIsOwnerAndRideIsEnded() {
        // Arrange
        Long rideId = 1L;
        Integer rating = 5;
        Ride ride = new Ride();
        ride.setRideStatus(RideStatus.ENDED);
        Driver driver = new Driver();
        ride.setDriver(driver);
        RiderDto expectedRiderDto = new RiderDto();

        when(rideService.getRideById(rideId)).thenReturn(ride);
        when(driverService.getCurrentDriver()).thenReturn(driver);
        when(ratingService.rateRider(ride, rating)).thenReturn(expectedRiderDto);

        // Act
        RiderDto result = driverService.rateRider(rideId, rating);

        // Assert
        assertEquals(expectedRiderDto, result);
        Mockito.verify(ratingService).rateRider(ride, rating);
    }

    /**
     * Test case for startRide method when the driver is valid, ride status is CONFIRMED, and OTP is correct.
     * This test verifies that the ride is started successfully, and all associated services are called.
     */
    @Test
    public void test_startRide_2() {
        // Arrange
        Long rideId = 1L;
        String otp = "1234";
        Ride ride = new Ride();
        ride.setRideStatus(RideStatus.CONFIRMED);
        ride.setOtp(otp);
        Driver driver = new Driver();
        ride.setDriver(driver);

        when(rideService.getRideById(rideId)).thenReturn(ride);
        when(driverService.getCurrentDriver()).thenReturn(driver);
        when(rideService.updateRideStatus(any(Ride.class), eq(RideStatus.ONGOING))).thenReturn(ride);

        RideDto expectedRideDto = new RideDto();
        when(modelMapper.map(ride, RideDto.class)).thenReturn(expectedRideDto);

        // Act
        RideDto result = driverService.startRide(rideId, otp);

        // Assert
        assertEquals(expectedRideDto, result);
        assertEquals(RideStatus.ONGOING, ride.getRideStatus());
        verify(paymentService).createNewPayment(ride);
        verify(ratingService).createNewRating(ride);
        verify(rideService).updateRideStatus(ride, RideStatus.ONGOING);
    }

    /**
     * Test case for starting a ride with an incorrect driver.
     * This test verifies that a RuntimeException is thrown when 
     * a driver attempts to start a ride they haven't accepted.
     */
    @Test
    public void test_startRide_incorrectDriver() {
        Long rideId = 1L;
        String otp = "1234";

        Ride ride = new Ride();
        ride.setDriver(new Driver());

        when(rideService.getRideById(rideId)).thenReturn(ride);
        when(driverService.getCurrentDriver()).thenReturn(new Driver());

        assertThrows(RuntimeException.class, () -> driverService.startRide(rideId, otp),
                "Driver cannot start a ride as he has not accepted it earlier");
    }

    /**
     * Test case for starting a ride with an incorrect ride status.
     * This test verifies that a RuntimeException is thrown when 
     * attempting to start a ride that is not in CONFIRMED status.
     */
    @Test
    public void test_startRide_incorrectRideStatus() {
        Long rideId = 1L;
        String otp = "1234";

        Driver driver = new Driver();
        Ride ride = new Ride();
        ride.setDriver(driver);
        ride.setRideStatus(RideStatus.ONGOING);

        when(rideService.getRideById(rideId)).thenReturn(ride);
        when(driverService.getCurrentDriver()).thenReturn(driver);

        assertThrows(RuntimeException.class, () -> driverService.startRide(rideId, otp),
                "Ride status is not CONFIRMED hence cannot be started, status: ONGOING");
    }

    /**
     * Test case for starting a ride with an invalid OTP.
     * This test verifies that a RuntimeException is thrown when 
     * the provided OTP does not match the ride's OTP.
     */
    @Test
    public void test_startRide_invalidOtp() {
        Long rideId = 1L;
        String providedOtp = "1234";
        String actualOtp = "5678";

        Driver driver = new Driver();
        Ride ride = new Ride();
        ride.setDriver(driver);
        ride.setRideStatus(RideStatus.CONFIRMED);
        ride.setOtp(actualOtp);

        when(rideService.getRideById(rideId)).thenReturn(ride);
        when(driverService.getCurrentDriver()).thenReturn(driver);

        assertThrows(RuntimeException.class, () -> driverService.startRide(rideId, providedOtp),
                "Otp is not valid, otp: " + providedOtp);
    }

    /**
     * Test case for startRide method when the driver is not the one who accepted the ride,
     * the ride status is not CONFIRMED, and the OTP is invalid.
     * Expected: RuntimeException is thrown with appropriate error messages.
     */
    @Test
    public void test_startRide_whenDriverNotMatchAndInvalidStatusAndInvalidOtp() {
        // Arrange
        Long rideId = 1L;
        String otp = "1234";
        Ride mockRide = mock(Ride.class);
        Driver mockDriver = mock(Driver.class);

        when(rideService.getRideById(rideId)).thenReturn(mockRide);
        when(driverService.getCurrentDriver()).thenReturn(mockDriver);
        when(mockRide.getDriver()).thenReturn(mock(Driver.class));
        when(mockRide.getRideStatus()).thenReturn(RideStatus.PENDING);
        when(mockRide.getOtp()).thenReturn("5678");

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            driverService.startRide(rideId, otp);
        });

        // Verify that the exception message matches the expected error
        assert(exception.getMessage().contains("Driver cannot start a ride as he has not accepted it earlier"));

        // Verify that the subsequent checks are not performed
        verify(mockRide, never()).setStartedAt(any());
        verify(rideService, never()).updateRideStatus(any(), any());
        verify(paymentService, never()).createNewPayment(any());
        verify(ratingService, never()).createNewRating(any());
        verify(modelMapper, never()).map(any(), any());
    }

    /**
     * Tests the updateDriverAvailability method to ensure it correctly updates
     * the driver's availability and returns the saved driver object.
     */
    @Test
    public void test_updateDriverAvailability_UpdatesAvailabilityAndReturnsSavedDriver() {
        // Arrange
        Driver driver = new Driver();
        driver.setAvailable(false);

        Driver savedDriver = new Driver();
        savedDriver.setAvailable(true);

        when(driverRepository.save(Mockito.any(Driver.class))).thenReturn(savedDriver);

        // Act
        Driver result = driverService.updateDriverAvailability(driver, true);

        // Assert
        assertEquals(true, driver.getAvailable());
        assertEquals(savedDriver, result);
    }

    /**
     * Tests the updateDriverAvailability method when the driver object is null.
     * This test verifies that the method handles null input gracefully by returning null.
     */
    @Test
    public void test_updateDriverAvailability_nullDriver() {
        Driver nullDriver = null;
        boolean available = true;

        Driver result = driverService.updateDriverAvailability(nullDriver, available);

        assertEquals(null, result);
    }

    /**
     * Tests the updateDriverAvailability method when the driverRepository.save() method throws an exception.
     * This test verifies that the method propagates the exception thrown by the repository.
     */
    @Test
    public void test_updateDriverAvailability_repositoryException() {
        Driver driver = new Driver();
        boolean available = false;

        when(driverRepository.save(Mockito.any(Driver.class))).thenThrow(new RuntimeException("Database error"));

        try {
            driverService.updateDriverAvailability(driver, available);
        } catch (RuntimeException e) {
            assertEquals("Database error", e.getMessage());
        }
    }

}
