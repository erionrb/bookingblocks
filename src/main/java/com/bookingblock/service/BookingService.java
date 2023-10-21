package com.bookingblock.service;

import com.bookingblock.model.Block;
import com.bookingblock.model.Booking;
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

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    public Booking createBooking(Booking booking) {
        validateSaveBooking(booking);
        return bookingRepository.save(booking);
    }

    public Booking updateBooking(Long id, Booking updatedBooking) {
        updatedBooking.setId(id);
        validateUpdateBooking(updatedBooking);
        return Optional.of(bookingRepository.save(updatedBooking)).get();
    }

    public void deleteBooking(Long id) {
        Booking booking = new Booking();
        booking.setId(id);

        validateDeleteBooking(booking);
        bookingRepository.deleteById(id);
    }

    public void validateSaveBooking(Booking booking) {
        validateOverlappingBlock(booking);
        validateOverlappingBooking(booking);
        validateBookingDates(booking);
    }

    public void validateUpdateBooking(Booking booking) {
        validateBookingDates(booking);
        validateBookingExists(booking);
        validateOverlappingBlock(booking);
        validateOverlappingOtherBooking(booking);
    }

    public void validateDeleteBooking(Booking booking) {
        validateBookingExists(booking);
    }

    public void validateOverlappingBooking(Booking booking) {
        List<Booking> bookings = bookingRepository.findByDateRange(booking.getStartDate(), booking.getEndDate());
        if (!bookings.isEmpty()) {
            throw new IllegalArgumentException("Booking overlaps with an existing booking");
        }
    }

    public void validateOverlappingOtherBooking(Booking booking) {
        List<Booking> bookings = bookingRepository.findByDateRange(booking.getStartDate(), booking.getEndDate());
        Predicate<Booking> isSameBooking = b -> b.getId().equals(booking.getId());
        if (!bookings.isEmpty()
                && !bookings.stream().allMatch(isSameBooking)) {
            throw new IllegalArgumentException("Booking overlaps with an existing booking by another guest");
        }
    }

    public void validateOverlappingBlock(Booking booking) {
        List<Block> blocks = blockService.findByDateRange(booking.getStartDate(), booking.getEndDate());
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
}
