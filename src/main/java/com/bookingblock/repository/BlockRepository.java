package com.bookingblock.repository;

import com.bookingblock.model.Block;
import com.bookingblock.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockRepository extends JpaRepository<Block, Long> {
}
