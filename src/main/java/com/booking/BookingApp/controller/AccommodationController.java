package com.booking.BookingApp.controller;

import com.booking.BookingApp.domain.Accommodation;
import com.booking.BookingApp.domain.enums.AccommodationType;
import com.booking.BookingApp.domain.enums.Status;
import com.booking.BookingApp.service.interfaces.IAccommodationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/accommodations")
public class AccommodationController {
    @Autowired
    private IAccommodationService accommodationService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Accommodation>> getAccommodations() {
        Collection<Accommodation> accommodations = accommodationService.findAll();
        return new ResponseEntity<Collection<Accommodation>>(accommodations, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Accommodation> getAccommodation(@PathVariable("id") Long id) {
        Accommodation accommodation = accommodationService.findOne(id);

        if (accommodation == null) {
            return new ResponseEntity<Accommodation>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Accommodation>(accommodation, HttpStatus.OK);
    }
    //get all accommodations for a date range
    @GetMapping(value = "/dates",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Accommodation>> getAccommodationsByDates(
            @RequestParam("begin") @DateTimeFormat(pattern="yyyy-MM-dd") Date begin,
            @RequestParam("end") @DateTimeFormat(pattern="yyyy-MM-dd") Date end) {
        List<Accommodation>accommodations=accommodationService.findAllForDates(begin,end);
        return new ResponseEntity<>(accommodations, HttpStatus.OK);
    }
    //get all accommodations for guest number
    @GetMapping(value="/guestNumber",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Accommodation>> getAccommodationsByGuestNumber(
            @RequestParam("guestNumber")int guestNumber) {
        List<Accommodation>accommodations=accommodationService.findAllForGuestNumber(guestNumber);
        return new ResponseEntity<>(accommodations, HttpStatus.OK);
    }
    //get all accommodations that are this type
    @GetMapping(value="/type",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Accommodation>> getAccommodationsByType(
            @RequestParam("type") AccommodationType type) {
        List<Accommodation>accommodations=accommodationService.findAllForType(type);
        return new ResponseEntity<>(accommodations, HttpStatus.OK);
    }
    //get all accommodations in price range
    @GetMapping(value ="/price",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Accommodation>> getAccommodationsByPrice(
            @RequestParam(value = "start_price", required = false) double startPrice,
            @RequestParam(value = "end_price", required = false) double endPrice) {
        List<Accommodation>accommodations=accommodationService.findAllForPrice(startPrice,endPrice);
        return new ResponseEntity<>(accommodations, HttpStatus.OK);
    }
    //get all accommodations that are this status
    @GetMapping(value = "/status",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Accommodation>> getAccommodationsByStatus(
            @RequestParam("status") Status status) {
        List<Accommodation>accommodations=accommodationService.findAllForStatus(status);
        return new ResponseEntity<>(accommodations, HttpStatus.OK);
    }
    //get all accommodations that are in a country/city
    @GetMapping(value = "/location",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Accommodation>> getAccommodationsByLocation(
            @RequestParam(value = "country", required = false) String country,
            @RequestParam(value = "city", required = false) String city) {
        List<Accommodation>accommodations=accommodationService.findAllForLocation(country,city);
        return new ResponseEntity<>(accommodations, HttpStatus.OK);
    }
    //get all accommodations that have these amenities
    @GetMapping(value = "/amenities",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Accommodation>> getAccommodationsByAmenities(
            @RequestParam("amenities") List<String> amenities) {
        List<Accommodation>accommodations=accommodationService.findAllForAmenities(amenities);
        return new ResponseEntity<>(accommodations, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Accommodation> createAccommodation(@RequestBody Accommodation accommodation) throws Exception {
        Accommodation savedAccommodation = accommodationService.create(accommodation);
        return new ResponseEntity<Accommodation>(savedAccommodation, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Accommodation> updateAccommodation(@RequestBody Accommodation accommodation, @PathVariable Long id)
            throws Exception {
        Accommodation accommodationForUpdate = accommodationService.findOne(id);
        //accommodationForUpdate.copyValues(accommodation);

        Accommodation updatedAccommodation = accommodationService.update(accommodationForUpdate);

        if (updatedAccommodation == null) {
            return new ResponseEntity<Accommodation>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<Accommodation>(updatedAccommodation, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Accommodation> deleteAccommodation(@PathVariable("id") Long id) {
        accommodationService.delete(id);
        return new ResponseEntity<Accommodation>(HttpStatus.NO_CONTENT);
    }

}
