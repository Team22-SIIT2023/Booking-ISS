package com.booking.BookingApp.controller;

import com.booking.BookingApp.domain.Accommodation;
import com.booking.BookingApp.domain.enums.AccommodationStatus;
import com.booking.BookingApp.domain.enums.AccommodationType;
import com.booking.BookingApp.domain.enums.Status;
import com.booking.BookingApp.dto.AccommodationDTO;
import com.booking.BookingApp.mapper.AccommodationDTOMapper;
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
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/accommodations")
public class AccommodationController {
    @Autowired
    private IAccommodationService accommodationService;
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccommodationDTO>> getAccommodations(
            @RequestParam(value = "begin", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date begin,
            @RequestParam(value = "end", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date end,
            @RequestParam(value = "guestNumber", defaultValue = "0") int guestNumber,
            @RequestParam(value = "type", required = false) AccommodationType type,
            @RequestParam(value = "start_price", defaultValue = "0") double startPrice,
            @RequestParam(value = "end_price", defaultValue = "0") double endPrice,
            @RequestParam(value = "status", required = false) AccommodationStatus status,
            @RequestParam(value = "country", required = false) String country,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "amenities", required = false) List<String> amenities
    ) {
        Collection<Accommodation>accommodations=accommodationService.findAll(begin,end,guestNumber,type,
                startPrice,endPrice,status,country,city,amenities);
        Collection<AccommodationDTO> accommodationDTOS = accommodations.stream()
                .map(AccommodationDTOMapper::fromAccommodationtoDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<Collection<AccommodationDTO>>(accommodationDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationDTO> getAccommodation(@PathVariable("id") Long id) {
        Accommodation accommodation = accommodationService.findOne(id);

        if (accommodation == null) {
            return new ResponseEntity<AccommodationDTO>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<AccommodationDTO>(AccommodationDTOMapper.fromAccommodationtoDTO(accommodation), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationDTO> createAccommodation(@RequestBody AccommodationDTO accommodation) throws Exception {
        Accommodation newAccommodation=AccommodationDTOMapper.fromDTOtoAccommodation(accommodation);
        Accommodation savedAccommodation = accommodationService.create(newAccommodation);
        return new ResponseEntity<AccommodationDTO>(AccommodationDTOMapper.fromAccommodationtoDTO(savedAccommodation), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationDTO> updateAccommodation(@RequestBody AccommodationDTO accommodationDTO, @PathVariable Long id)
            throws Exception {
        Accommodation accommodationForUpdate = accommodationService.findOne(id);
        Accommodation accommodation=AccommodationDTOMapper.fromDTOtoAccommodation(accommodationDTO);
        Accommodation updatedAccommodation = accommodationService.update(accommodation,accommodationForUpdate);

        if (updatedAccommodation == null) {
            return new ResponseEntity<AccommodationDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<AccommodationDTO>(AccommodationDTOMapper.fromAccommodationtoDTO(updatedAccommodation), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<AccommodationDTO> deleteAccommodation(@PathVariable("id") Long id) {
        accommodationService.delete(id);
        return new ResponseEntity<AccommodationDTO>(HttpStatus.NO_CONTENT);
    }

}
