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

    public Block getBlockById(Long id) {
        Optional<Block> block =  bookingRepository.findById(id);
        return block.orElseThrow(() -> new IllegalArgumentException("Block " + id + " does not exist"));
    }

    public Block createBlock(Block booking) {
        return bookingRepository.save(booking);
    }

    public Block updateBlock(Long id, Block block) {
        block.setId(id);
        validateUpdateBlock(block);
        return bookingRepository.save(block);
    }

    public void deleteBlock(Long id) {
        Block block = new Block();
        block.setId(id);

        validateDeleteBlock(block);
        bookingRepository.deleteById(id);
    }

    public List<Block> findByDateRange(Date startDate, Date endDate) {
        return bookingRepository.findByDateRange(startDate, endDate);
    }

    public void validateUpdateBlock(Block block) {
        validateBlockExists(block);
    }

    public void validateDeleteBlock(Block block) {
        validateBlockExists(block);
    }

    public void validateBlockExists(Block block) {
        if (!bookingRepository.existsById(block.getId())) {
            throw new IllegalArgumentException("Block " + block.getId() + " does not exist");
        }
    }
}
