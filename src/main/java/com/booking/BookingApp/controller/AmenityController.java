package com.booking.BookingApp.controller;

import com.booking.BookingApp.domain.Amenity;
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
    public ResponseEntity<Collection<Amenity>> getAmenities() {
        Collection<Amenity> amenities = amenityService.findAll();
        return new ResponseEntity<Collection<Amenity>>(amenities, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Amenity> getById(@PathVariable("id") Long id) {
        Amenity amenity = amenityService.findById(id);
        return new ResponseEntity<Amenity>(amenity, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Amenity> createRequest(@RequestBody Amenity amenity) {
        Amenity savedAmenity = amenityService.create(amenity);
        return new ResponseEntity<Amenity>(savedAmenity, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Amenity> updateRequest(@PathVariable("id") Long id) {
        Amenity amenityForUpdated = amenityService.findById(id);
        // zavrsiti !!!!!
        return new ResponseEntity<Amenity>(amenityForUpdated, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Amenity> deleteRequest(@PathVariable("id") Long id) {
        amenityService.delete(id);
        return new ResponseEntity<Amenity>(HttpStatus.NO_CONTENT);
    }
}
