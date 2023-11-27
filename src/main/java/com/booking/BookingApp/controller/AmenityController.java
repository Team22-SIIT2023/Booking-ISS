package com.booking.BookingApp.controller;

import com.booking.BookingApp.domain.Amenity;
import com.booking.BookingApp.dto.AmenityDTO;
import com.booking.BookingApp.dto.CommentsDTO;
import com.booking.BookingApp.mapper.AmenityDTOMapper;
import com.booking.BookingApp.mapper.CommentsDTOMapper;
import com.booking.BookingApp.service.AmenityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/amenities")
public class AmenityController {

    @Autowired
    private AmenityService amenityService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AmenityDTO>> getAmenities() {
        Collection<Amenity> amenities = amenityService.findAll();

        Collection<AmenityDTO> amenitiesDTO = amenities.stream()
                .map(AmenityDTOMapper::fromAmenitytoDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<Collection<AmenityDTO>>(amenitiesDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AmenityDTO> getById(@PathVariable("id") Long id) {
        Amenity amenity = amenityService.findById(id);
        return new ResponseEntity<AmenityDTO>(new AmenityDTO(amenity), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AmenityDTO> createRequest(@RequestBody AmenityDTO amenityDTO) {
        Amenity amenityModel = AmenityDTOMapper.fromDTOtoAmenity(amenityDTO);
        Amenity savedAmenity = amenityService.create(amenityModel);
        return new ResponseEntity<AmenityDTO>(new AmenityDTO(savedAmenity), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AmenityDTO> updateRequest(@RequestBody AmenityDTO amenityDTO, @PathVariable("id") Long id) {
        Amenity amenityForUpdate = amenityService.findById(id);
        amenityForUpdate.setId(amenityDTO.getId());
        amenityForUpdate.setName(amenityDTO.getName());
        amenityForUpdate.setIcon(amenityDTO.getIcon());
        Amenity updatedAmenity = amenityService.update(amenityForUpdate);
        return new ResponseEntity<AmenityDTO>(new AmenityDTO(updatedAmenity), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<AmenityDTO> deleteRequest(@PathVariable("id") Long id) {
        amenityService.delete(id);
        return new ResponseEntity<AmenityDTO>(HttpStatus.NO_CONTENT);
    }
}
