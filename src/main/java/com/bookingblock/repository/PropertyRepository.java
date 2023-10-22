package com.bookingblock.repository;

import com.bookingblock.model.Booking;
import com.bookingblock.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, Long> {
}
