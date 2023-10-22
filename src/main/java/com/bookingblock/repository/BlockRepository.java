package com.bookingblock.repository;

import com.bookingblock.model.Block;
import com.bookingblock.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface BlockRepository extends JpaRepository<Block, Long> {

    @Query("SELECT b FROM Block b WHERE (b.startDate <= :endDate) AND (b.endDate >= :startDate) AND (b.property.id = :propertyId)")
    List<Block> findByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("propertyId") Long propertyId);
}