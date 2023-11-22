package com.booking.BookingApp.controller;

import com.booking.BookingApp.domain.Amenity;
import com.booking.BookingApp.dto.AmenityDTO;
import com.booking.BookingApp.service.AmenityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/amenities")
public class AmenityController {

    @Autowired
    private AmenityService amenityService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AmenityDTO>> getAmenities() {
        Collection<AmenityDTO> amenities = amenityService.findAll();
        return new ResponseEntity<Collection<AmenityDTO>>(amenities, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AmenityDTO> getById(@PathVariable("id") Long id) {
        AmenityDTO amenity = amenityService.findById(id);
        return new ResponseEntity<AmenityDTO>(amenity, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AmenityDTO> createRequest(@RequestBody AmenityDTO amenity) {
        AmenityDTO savedAmenity = amenityService.create(amenity);
        return new ResponseEntity<AmenityDTO>(savedAmenity, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AmenityDTO> updateRequest(@PathVariable("id") Long id) {
        AmenityDTO amenityForUpdated = amenityService.findById(id);
        // zavrsiti !!!!!
        return new ResponseEntity<AmenityDTO>(amenityForUpdated, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<AmenityDTO> deleteRequest(@PathVariable("id") Long id) {
        amenityService.delete(id);
        return new ResponseEntity<AmenityDTO>(HttpStatus.NO_CONTENT);
    }
}
