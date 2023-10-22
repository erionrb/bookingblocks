package com.bookingblock.controller;

import com.bookingblock.model.Block;
import com.bookingblock.model.ResponseData;
import com.bookingblock.service.BlockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blocks")
@Api(tags = "Block Endpoints")
public class BlockController {

    private final BlockService blockService;

    @Autowired
    public BlockController(BlockService blockService) {
        this.blockService = blockService;
    }

    @GetMapping("/")
    @ApiOperation(value = "Get all blocks")
    public ResponseEntity<ResponseData<List<Block>>> getBlocks() {
        try {
            List<Block> blocks = blockService.getAllBlocks();
            return new ResponseEntity<>(new ResponseData<>(blocks), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get block by id")
    public ResponseEntity<ResponseData<Block>> getBlock(@PathVariable("id") Long id) {
        try {
            Block block = blockService.getBlockById(id);
            return new ResponseEntity<>(new ResponseData<>(block), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseData<>(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/")
    @ApiOperation(value = "Create block")
    public ResponseEntity<ResponseData<Block>> createBlock(@RequestBody Block block) {
        try {
            Block createdBlock = blockService.createBlock(block);
            return new ResponseEntity<>(new ResponseData<>(createdBlock), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseData<>(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update block")
    public ResponseEntity<ResponseData<Block>> updateBlock(@PathVariable("id") Long id, @RequestBody Block updatedBlock) {
        try {
            return new ResponseEntity<>(new ResponseData<>(blockService.updateBlock(id, updatedBlock)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseData<>(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete block")
    public ResponseEntity<ResponseData<Block>> deleteBlock(@PathVariable("id") Long id) {
        try {
            blockService.deleteBlock(id);
            return new ResponseEntity<>(new ResponseData<>(null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseData<>(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
}
