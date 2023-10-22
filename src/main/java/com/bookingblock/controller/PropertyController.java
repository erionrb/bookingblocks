package com.bookingblock.controller;

import com.bookingblock.model.Block;
import com.bookingblock.model.Property;
import com.bookingblock.model.ResponseData;
import com.bookingblock.service.PropertyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/properties")
@Api(tags = "Property Endpoints")
public class PropertyController {

    private final PropertyService propertyService;

    @Autowired
    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @GetMapping("/")
    @ApiOperation(value = "Get all properties")
    public ResponseEntity<ResponseData<List<Property>>> getBlocks() {
        try {
            List<Property> properties = propertyService.getAllProperties();
            return new ResponseEntity<>(new ResponseData<>(properties), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/")
    @ApiOperation(value = "Create property")
    public ResponseEntity<ResponseData<Property>> createBlock(@RequestBody Property property) {
        try {
            Property createdProperty = propertyService.createProperty(property);
            return new ResponseEntity<>(new ResponseData<>(createdProperty), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseData<>(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
