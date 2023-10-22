package com.bookingblock.repository;

import com.bookingblock.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT b FROM Booking b WHERE (b.startDate <= :endDate) AND (b.endDate >= :startDate) AND (b.property.id = :propertyId)")
    List<Booking> findByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("propertyId") Long propertyId);
}
