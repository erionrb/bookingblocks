package com.bookingblock.controller;

import com.bookingblock.model.Block;
import com.bookingblock.service.BlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/blocks")
public class BlockController {

    private final BlockService blockService;

    @Autowired
    public BlockController(BlockService blockService) {
        this.blockService = blockService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Block>> getBlocks() {
        List<Block> blocks = blockService.getAllBlocks();
        return new ResponseEntity<>(blocks, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Block> getBlock(@PathVariable("id") Long id) {
        Optional<Block> block = blockService.getBlockById(id);
        return block
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/")
    public ResponseEntity<Block> createBlock(@RequestBody Block block) {
        Block createdBlock = blockService.createBlock(block);
        return new ResponseEntity<>(createdBlock, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Block> updateBlock(@PathVariable("id") Long id, @RequestBody Block updatedBlock) {
        Optional<Block> block = blockService.updateBlock(id, updatedBlock);
        return block
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlock(@PathVariable("id") Long id) {
        boolean deleted = blockService.deleteBlock(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
