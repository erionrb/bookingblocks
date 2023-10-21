package com.bookingblock.service;

import com.bookingblock.model.Block;
import com.bookingblock.repository.BlockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BlockService {

    @Autowired
    private BlockRepository bookingRepository;

    public List<Block> getAllBlocks() {
        return bookingRepository.findAll();
    }

    public Optional<Block> getBlockById(Long id) {
        return bookingRepository.findById(id);
    }

    public Block createBlock(Block booking) {
        return bookingRepository.save(booking);
    }

    public Optional<Block> updateBlock(Long id, Block updatedBlock) {
        Optional<Block> existingBlock = bookingRepository.findById(id);
        if (existingBlock.isPresent()) {
            updatedBlock.setId(existingBlock.get().getId());
            return Optional.of(bookingRepository.save(updatedBlock));
        }
        return Optional.empty();
    }

    public boolean deleteBlock(Long id) {
        if (bookingRepository.existsById(id)) {
            bookingRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Block> findByDateRange(Date startDate, Date endDate) {
        return bookingRepository.findByDateRange(startDate, endDate);
    }
}
