package com.bookingblock.service;

import com.bookingblock.model.Block;
import com.bookingblock.model.Booking;
import com.bookingblock.model.Property;
import com.bookingblock.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BlockService blockService;

    @Autowired
    private PropertyService propertyService;

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBookingById(Long id) {
        Optional<Booking> booking = bookingRepository.findById(id);
        return booking.orElseThrow(() -> new IllegalArgumentException("Booking " + id + " does not exist"));
    }

    public Booking createBooking(Booking booking) {
        validateSaveBooking(booking);
        return bookingRepository.save(booking);
    }

    public Booking updateBooking(Long id, Booking updatedBooking) throws Exception {
        updatedBooking.setId(id);
        validateUpdateBooking(updatedBooking);
        Optional<Booking> booking = Optional.of(bookingRepository.save(updatedBooking));
        return booking.orElseThrow(() -> new Exception("Failed to save booking"));
    }

    public void deleteBooking(Long id) {
        Booking booking = new Booking();
        booking.setId(id);

        validateDeleteBooking(booking);
        bookingRepository.deleteById(id);
    }

    public void validateSaveBooking(Booking booking) {
        validateParams(booking);
        validatePropertyExists(booking);
        validateOverlappingBlock(booking);
        validateOverlappingBooking(booking);
        validateBookingDates(booking);
    }

    public void validateUpdateBooking(Booking booking) {
        validateParams(booking);
        validatePropertyExists(booking);
        validateBookingDates(booking);
        validateBookingExists(booking);
        validateOverlappingBlock(booking);
        validateOverlappingOtherBooking(booking);
    }

    public void validateDeleteBooking(Booking booking) {
        validateBookingExists(booking);
    }

    public void validateParams(Booking booking) {
        if (booking.getStartDate() == null) throw new IllegalArgumentException("Start date is required");
        if (booking.getEndDate() == null) throw new IllegalArgumentException("End date is required");
        if (booking.getGuestName() == null) throw new IllegalArgumentException("Guest name is required");
        if (booking.getProperty() == null || booking.getProperty().getId() == null) throw new IllegalArgumentException("Property is required");
    }

    public void validateOverlappingBooking(Booking booking) {
        List<Booking> bookings = bookingRepository.findByDateRange(booking.getStartDate(), booking.getEndDate(), booking.getProperty().getId());
        if (!bookings.isEmpty()) {
            throw new IllegalArgumentException("Booking overlaps with an existing booking");
        }
    }

    public void validateOverlappingOtherBooking(Booking booking) {
        List<Booking> bookings = bookingRepository.findByDateRange(booking.getStartDate(), booking.getEndDate(), booking.getProperty().getId());
        Predicate<Booking> isSameBooking = b -> b.getId().equals(booking.getId());
        if (!bookings.isEmpty()
                && !bookings.stream().allMatch(isSameBooking)) {
            throw new IllegalArgumentException("Booking overlaps with an existing booking by another guest");
        }
    }

    public void validateOverlappingBlock(Booking booking) {
        Long propertyId = booking.getProperty().getId();
        List<Block> blocks = blockService.findByDateRange(booking.getStartDate(), booking.getEndDate(), propertyId);
        if (!blocks.isEmpty()) throw new IllegalArgumentException("Overlaps with existing block");
    }

    public void validateBookingDates(Booking booking) {
        if (booking.getStartDate().after(booking.getEndDate()))
            throw new IllegalArgumentException("Start date must be before end date");
    }

    public void validateBookingExists(Booking booking) {
        if (!bookingRepository.existsById(booking.getId()))
            throw new IllegalArgumentException("Booking " + booking.getId() + " does not exist");
    }

    public void validatePropertyExists(Booking booking) {
        Property property =  propertyService.getPropertyById(booking.getProperty().getId());
        if (property == null) throw new IllegalArgumentException("Property " + booking.getProperty().getId() + " does not exist");

        booking.setProperty(property);
    }
}
