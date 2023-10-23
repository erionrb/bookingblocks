package com.bookingblock.service;

import com.bookingblock.model.Block;
import com.bookingblock.model.Booking;
import com.bookingblock.model.Property;
import com.bookingblock.repository.BookingRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BookingServiceTest {

    @InjectMocks
    private BookingService bookingService;

    @Mock
    private BlockService blockService;

    @Mock
    private PropertyService propertyService;

    @Mock
    private BookingRepository bookingRepository;

    @BeforeAll
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetBookingById_Success() {
        Long bookingId = 1L;
        Booking booking = new Booking();
        booking.setId(bookingId);
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));

        Booking result = bookingService.getBookingById(bookingId);
        assertNotNull(result);
        assertEquals(bookingId, result.getId());
    }

    @Test
    void testGetBookingById_NotFound() {
        Long bookingId = 1L;
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            bookingService.getBookingById(bookingId);
        });
    }

    @Test
    void testCreateBooking_Success() {
        Long propertyId = 1L;
        Property property = new Property("Malibu point 10880, Malibu, CA");
        property.setId(propertyId);

        Booking booking = new Booking();
        booking.setId(1L);
        booking.setStartDate(Date.valueOf("2023-10-21"));
        booking.setEndDate(Date.valueOf("2023-10-23"));
        booking.setGuestName("Erion Barasuol");
        booking.setProperty(property);

        when(bookingRepository.findByDateRange(any(Date.class), any(Date.class), any(Long.class))).thenReturn(new ArrayList<>());
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);
        when(blockService.findByDateRange(any(Date.class), any(Date.class), any(Long.class))).thenReturn(new ArrayList<>());

        Booking result = bookingService.createBooking(booking);

        verify(bookingRepository, times(1)).save(booking);
        assertNotNull(result);
        assertEquals(booking.getId(), result.getId());
    }

    @Test
    void testCreateBooking_FailedEndDateGtStartDate() {
        Long propertyId = 1L;
        Property property = new Property("Malibu point 10880, Malibu, CA");
        property.setId(propertyId);

        Booking booking = new Booking();
        booking.setId(1L);
        booking.setStartDate(Date.valueOf("2023-10-23"));
        booking.setEndDate(Date.valueOf("2023-10-21"));
        booking.setGuestName("Erion Barasuol");
        booking.setProperty(property);

        when(bookingRepository.findByDateRange(any(Date.class), any(Date.class), any(Long.class))).thenReturn(new ArrayList<>());
        when(blockService.findByDateRange(any(Date.class), any(Date.class), any(Long.class))).thenReturn(new ArrayList<>());

        assertThrows(IllegalArgumentException.class, () -> {
            bookingService.createBooking(booking);
        });
    }

    @Test
    void testCreateBooking_FailedOverlapBooking() {
        Long propertyId = 1L;
        Property property = new Property("Malibu point 10880, Malibu, CA");
        property.setId(propertyId);

        Booking booking = new Booking();
        booking.setId(1L);
        booking.setStartDate(Date.valueOf("2023-10-21"));
        booking.setEndDate(Date.valueOf("2023-10-22"));
        booking.setGuestName("Erion Barasuol");
        booking.setProperty(property);

        Booking overlapBooking = new Booking();
        overlapBooking.setId(2L);
        overlapBooking.setStartDate(Date.valueOf("2023-10-19"));
        overlapBooking.setEndDate(Date.valueOf("2023-10-25"));
        overlapBooking.setProperty(property);

        when(bookingRepository.findByDateRange(any(Date.class), any(Date.class), any(Long.class))).thenReturn(List.of(overlapBooking));
        when(blockService.findByDateRange(any(Date.class), any(Date.class), any(Long.class))).thenReturn(new ArrayList<>());

        assertThrows(IllegalArgumentException.class, () -> {
            bookingService.createBooking(booking);
        });
    }

    @Test
    void testCreateBooking_FailedOverlapBlock() {
        Long propertyId = 1L;
        Property property = new Property("Malibu point 10880, Malibu, CA");
        property.setId(propertyId);

        Booking booking = new Booking();
        booking.setId(1L);
        booking.setStartDate(Date.valueOf("2023-10-21"));
        booking.setEndDate(Date.valueOf("2023-10-22"));
        booking.setGuestName("Erion Barasuol");
        booking.setProperty(property);

        Block overlapBlock = new Block();
        overlapBlock.setId(2L);
        overlapBlock.setStartDate(Date.valueOf("2023-10-19"));
        overlapBlock.setEndDate(Date.valueOf("2023-10-25"));

        when(bookingRepository.findByDateRange(any(Date.class), any(Date.class), any(Long.class))).thenReturn(new ArrayList<>());
        when(blockService.findByDateRange(any(Date.class), any(Date.class), any(Long.class))).thenReturn(List.of(overlapBlock));
        when(propertyService.getPropertyById(any(Long.class))).thenReturn(property);

        assertThrows(IllegalArgumentException.class, () -> {
            bookingService.createBooking(booking);
        });
    }

    @Test
    void testCreateBooking_FailedInvalidStartDate() {
        Long propertyId = 1L;
        Property property = new Property("Malibu point 10880, Malibu, CA");
        property.setId(propertyId);

        Booking booking = new Booking();
        booking.setId(1L);
        booking.setStartDate(null);
        booking.setEndDate(Date.valueOf("2023-10-22"));
        booking.setGuestName("Erion Barasuol");
        booking.setProperty(property);

        assertThrows(IllegalArgumentException.class, () -> {
            bookingService.createBooking(booking);
        });
    }

    @Test
    void testCreateBooking_FailedStartInvalidEndDate() {
        Booking booking = new Booking();
        booking.setId(1L);
        booking.setStartDate(Date.valueOf("2023-10-22"));
        booking.setEndDate(null);
        booking.setGuestName("Erion Barasuol");

        assertThrows(IllegalArgumentException.class, () -> {
            bookingService.createBooking(booking);
        });
    }

    @Test
    void testCreateBooking_FailedInvalidGuest() {
        Booking booking = new Booking();
        booking.setId(1L);
        booking.setStartDate(Date.valueOf("2023-10-22"));
        booking.setEndDate(Date.valueOf("2023-10-23"));
        booking.setGuestName(null);

        assertThrows(IllegalArgumentException.class, () -> {
            bookingService.createBooking(booking);
        });
    }

    @Test
    void testCreateBooking_FailedNoProperty() {
        Booking booking = new Booking();
        booking.setId(1L);
        booking.setStartDate(Date.valueOf("2023-10-22"));
        booking.setEndDate(Date.valueOf("2023-10-23"));
        booking.setGuestName("Erion Barasuol");

        assertThrows(IllegalArgumentException.class, () -> {
            bookingService.createBooking(booking);
        });
    }
}
