package com.bookingblock.controller;

import com.bookingblock.model.Booking;
import com.bookingblock.model.ResponseData;
import com.bookingblock.service.BookingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@Api(tags = "Booking Endpoints")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/")
    @ApiOperation(value = "Get all bookings")
    public ResponseEntity<ResponseData<List<Booking>>> getBookings() {
        try {
            List<Booking> bookings = bookingService.getAllBookings();
            return new ResponseEntity<>(new ResponseData<>(bookings), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseData<>(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get booking by id")
    public ResponseEntity<ResponseData<Booking>> getBooking(@PathVariable("id") Long id) {
        try {
            Booking booking = bookingService.getBookingById(id);
            return new ResponseEntity<>(new ResponseData<>(booking), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseData<>(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    @ApiOperation(value = "Create booking")
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
    @ApiOperation(value = "Update booking")
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
    @ApiOperation(value = "Delete booking")
    public ResponseEntity<ResponseData<Booking>> deleteBooking(@PathVariable("id") Long id) {
        try {
            bookingService.deleteBooking(id);
            return new ResponseEntity<>(new ResponseData<>(null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseData<>(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
}
