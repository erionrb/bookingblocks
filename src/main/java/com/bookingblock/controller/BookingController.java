package com.bookingblock.controller;

import com.bookingblock.model.Booking;
import com.bookingblock.model.ResponseData;
import com.bookingblock.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Booking>> getBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBooking(@PathVariable("id") Long id) {
        Optional<Booking> booking = bookingService.getBookingById(id);
        return booking
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/")
    public ResponseEntity<ResponseData<Booking>> createBooking(@RequestBody Booking booking) {
        try {
            Booking createdBooking = bookingService.createBooking(booking);
            return new ResponseEntity<>(new ResponseData<>(createdBooking), HttpStatus.CREATED);
        } catch (Exception e) {
            HttpStatus status = e.getMessage().contains("overlap") ? HttpStatus.CONFLICT : HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<>(new ResponseData<>(e.getMessage()), status);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseData<Booking>> updateBooking(@PathVariable("id") Long id, @RequestBody Booking updatedBooking) {
        try {
            Booking booking = bookingService.updateBooking(id, updatedBooking);
            return new ResponseEntity<>(new ResponseData<>(booking), HttpStatus.OK);
        } catch (Exception e) {
            HttpStatus status = e.getMessage().contains("overlap") ? HttpStatus.CONFLICT : HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<>(new ResponseData<>(e.getMessage()), status);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable("id") Long id) {
        boolean deleted = bookingService.deleteBooking(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
