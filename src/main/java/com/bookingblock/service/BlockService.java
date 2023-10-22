package com.bookingblock.service;

import com.bookingblock.model.Block;
import com.bookingblock.model.Property;
import com.bookingblock.repository.BlockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BlockService {

    @Autowired
    private BlockRepository blockRepository;

    @Autowired
    private PropertyService propertyService;

    public List<Block> getAllBlocks() {
        return blockRepository.findAll();
    }

    public Block getBlockById(Long id) {
        Optional<Block> block =  blockRepository.findById(id);
        return block.orElseThrow(() -> new IllegalArgumentException("Block " + id + " does not exist"));
    }

    public Block createBlock(Block block) {
        validateCreateBlock(block);
        return blockRepository.save(block);
    }

    public Block updateBlock(Long id, Block block) {
        block.setId(id);
        validateUpdateBlock(block);
        return blockRepository.save(block);
    }

    public void deleteBlock(Long id) {
        Block block = new Block();
        block.setId(id);

        validateDeleteBlock(block);
        blockRepository.deleteById(id);
    }

    public List<Block> findByDateRange(Date startDate, Date endDate, Long propertyId) {
        return blockRepository.findByDateRange(startDate, endDate, propertyId);
    }

    public void validateCreateBlock(Block block) {
        validateParams(block);
        validatePropertyExists(block);
        validateBlockDates(block);
    }

    public void validateUpdateBlock(Block block) {
        validateBlockExists(block);
    }

    public void validateDeleteBlock(Block block) {
        validateBlockExists(block);
    }

    public void validateBlockExists(Block block) {
        if (!blockRepository.existsById(block.getId())) {
            throw new IllegalArgumentException("Block " + block.getId() + " does not exist");
        }
    }

    public void validateParams(Block block) {
        if (block.getStartDate() == null) {
            throw new IllegalArgumentException("Start date is required");
        }
        if (block.getEndDate() == null) {
            throw new IllegalArgumentException("End date is required");
        }
        if (block.getName() == null) {
            throw new IllegalArgumentException("Name is required");
        }

        if (block.getProperty() == null || block.getProperty().getId() == null) {
            throw new IllegalArgumentException("Property is required");
        }
    }

    public void validateBlockDates(Block block) {
        if (block.getStartDate().after(block.getEndDate())) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
    }

    public void validatePropertyExists(Block block) {
        Property property =  propertyService.getPropertyById(block.getProperty().getId());
        if (property == null) throw new IllegalArgumentException("Property " + block.getProperty().getId() + " does not exist");

        block.setProperty(property);
    }
}
