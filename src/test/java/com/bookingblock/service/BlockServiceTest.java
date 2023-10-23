package com.bookingblock.service;

import com.bookingblock.model.Block;
import com.bookingblock.model.Property;
import com.bookingblock.repository.BlockRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BlockServiceTest {

    @InjectMocks
    private BlockService blockService;

    @Mock
    private PropertyService propertyService;

    @Mock
    private BlockRepository blockRepository;

    @BeforeAll
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetBlockById_Success() {
        Long blockId = 1L;
        Property property = new Property("Malibu point 10880, Malibu, CA");
        property.setId(1L);

        Block block = new Block();
        block.setId(blockId);
        block.setProperty(property);
        when(blockRepository.findById(blockId)).thenReturn(Optional.of(block));

        Block result = blockService.getBlockById(blockId);
        assertNotNull(result);
        assertEquals(blockId, result.getId());
    }

    @Test
    void testGetBlockById_NotFound() {
        Long blockId = 1L;
        when(blockRepository.findById(blockId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            blockService.getBlockById(blockId);
        });
    }

    @Test
    void testCreateBlock_Success() {
        Long propertyId = 1L;
        Property property = new Property("Malibu point 10880, Malibu, CA");
        property.setId(propertyId);

        Block block = new Block();
        block.setId(1L);
        block.setStartDate(Date.valueOf("2023-10-21"));
        block.setEndDate(Date.valueOf("2023-10-23"));
        block.setName("Blocked Event");
        block.setProperty(property);

        when(blockRepository.findByDateRange(any(Date.class), any(Date.class), any(Long.class))).thenReturn(new ArrayList<>());
        when(blockRepository.save(block)).thenReturn(block);
        when(propertyService.getPropertyById(any(Long.class))).thenReturn(property);

        Block result = blockService.createBlock(block);

        verify(blockRepository, times(1)).save(block);
        assertNotNull(result);
        assertEquals(block.getId(), result.getId());
    }

    @Test
    void testCreateBlock_FailedEndDateGtStartDate() {
        Block block = new Block();
        block.setId(1L);
        block.setStartDate(Date.valueOf("2023-10-23"));
        block.setEndDate(Date.valueOf("2023-10-21"));
        block.setName("Blocked Event");

        assertThrows(IllegalArgumentException.class, () -> {
            blockService.createBlock(block);
        });
    }

    @Test
    void testCreateBlock_FailedInvalidStartDate() {
        Block block = new Block();
        block.setId(1L);
        block.setStartDate(null);
        block.setEndDate(Date.valueOf("2023-10-22"));
        block.setName("Blocked Event");

        assertThrows(IllegalArgumentException.class, () -> {
            blockService.createBlock(block);
        });
    }

    @Test
    void testCreateBlock_FailedInvalidEndDate() {
        Block block = new Block();
        block.setId(1L);
        block.setStartDate(Date.valueOf("2023-10-22"));
        block.setEndDate(null);
        block.setName("Blocked Event");

        assertThrows(IllegalArgumentException.class, () -> {
            blockService.createBlock(block);
        });
    }

    @Test
    void testCreateBlock_FailedInvalidName() {
        Block block = new Block();
        block.setId(1L);
        block.setStartDate(Date.valueOf("2023-10-22"));
        block.setEndDate(Date.valueOf("2023-10-23"));
        block.setName(null);

        assertThrows(IllegalArgumentException.class, () -> {
            blockService.createBlock(block);
        });
    }
}
