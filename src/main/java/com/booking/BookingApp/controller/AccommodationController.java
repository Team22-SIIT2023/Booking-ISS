package com.booking.BookingApp.controller;

import com.booking.BookingApp.domain.Accommodation;
import com.booking.BookingApp.domain.enums.AccommodationType;
import com.booking.BookingApp.domain.enums.Status;
import com.booking.BookingApp.dto.AccommodationDTO;
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
    public ResponseEntity<Collection<AccommodationDTO>> getAccommodations() {
        Collection<AccommodationDTO> accommodations = accommodationService.findAll();
        return new ResponseEntity<Collection<AccommodationDTO>>(accommodations, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationDTO> getAccommodation(@PathVariable("id") Long id) {
        AccommodationDTO accommodation = accommodationService.findOne(id);

        if (accommodation == null) {
            return new ResponseEntity<AccommodationDTO>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<AccommodationDTO>(accommodation, HttpStatus.OK);
    }
    //get all accommodations for a date range
    @GetMapping(value = "/dates",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AccommodationDTO>> getAccommodationsByDates(
            @RequestParam("begin") @DateTimeFormat(pattern="yyyy-MM-dd") Date begin,
            @RequestParam("end") @DateTimeFormat(pattern="yyyy-MM-dd") Date end) {
        List<AccommodationDTO>accommodations=accommodationService.findAllForDates(begin,end);
        return new ResponseEntity<>(accommodations, HttpStatus.OK);
    }
    //get all accommodations for guest number
    @GetMapping(value="/guestNumber",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AccommodationDTO>> getAccommodationsByGuestNumber(
            @RequestParam("guestNumber")int guestNumber) {
        List<AccommodationDTO>accommodations=accommodationService.findAllForGuestNumber(guestNumber);
        return new ResponseEntity<>(accommodations, HttpStatus.OK);
    }
    //get all accommodations that are this type
    @GetMapping(value="/type",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AccommodationDTO>> getAccommodationsByType(
            @RequestParam("type") AccommodationType type) {
        List<AccommodationDTO>accommodations=accommodationService.findAllForType(type);
        return new ResponseEntity<>(accommodations, HttpStatus.OK);
    }
    //get all accommodations in price range
    @GetMapping(value ="/price",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AccommodationDTO>> getAccommodationsByPrice(
            @RequestParam(value = "start_price", required = false) double startPrice,
            @RequestParam(value = "end_price", required = false) double endPrice) {
        List<AccommodationDTO>accommodations=accommodationService.findAllForPrice(startPrice,endPrice);
        return new ResponseEntity<>(accommodations, HttpStatus.OK);
    }
    //get all accommodations that are this status
    @GetMapping(value = "/status",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AccommodationDTO>> getAccommodationsByStatus(
            @RequestParam("status") Status status) {
        List<AccommodationDTO>accommodations=accommodationService.findAllForStatus(status);
        return new ResponseEntity<>(accommodations, HttpStatus.OK);
    }
    //get all accommodations that are in a country/city
    @GetMapping(value = "/location",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AccommodationDTO>> getAccommodationsByLocation(
            @RequestParam(value = "country", required = false) String country,
            @RequestParam(value = "city", required = false) String city) {
        List<AccommodationDTO>accommodations=accommodationService.findAllForLocation(country,city);
        return new ResponseEntity<>(accommodations, HttpStatus.OK);
    }
    //get all accommodations that have these amenities
    @GetMapping(value = "/amenities",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AccommodationDTO>> getAccommodationsByAmenities(
            @RequestParam("amenities") List<String> amenities) {
        List<AccommodationDTO>accommodations=accommodationService.findAllForAmenities(amenities);
        return new ResponseEntity<>(accommodations, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationDTO> createAccommodation(@RequestBody AccommodationDTO accommodation) throws Exception {
        AccommodationDTO savedAccommodation = accommodationService.create(accommodation);
        return new ResponseEntity<AccommodationDTO>(savedAccommodation, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationDTO> updateAccommodation(@RequestBody AccommodationDTO accommodation, @PathVariable Long id)
            throws Exception {
        AccommodationDTO accommodationForUpdate = accommodationService.findOne(id);
        //accommodationForUpdate.copyValues(accommodation);

        AccommodationDTO updatedAccommodation = accommodationService.update(accommodationForUpdate);

        if (updatedAccommodation == null) {
            return new ResponseEntity<AccommodationDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<AccommodationDTO>(updatedAccommodation, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<AccommodationDTO> deleteAccommodation(@PathVariable("id") Long id) {
        accommodationService.delete(id);
        return new ResponseEntity<AccommodationDTO>(HttpStatus.NO_CONTENT);
    }

}
