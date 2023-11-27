package com.booking.BookingApp.controller;

import com.booking.BookingApp.domain.FavoriteAccommodation;
import com.booking.BookingApp.dto.FavoriteAccommodationDTO;
import com.booking.BookingApp.service.interfaces.IFavoriteAccommodationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/favoriteAccommodations")
public class FavoriteAccommodationController {
    @Autowired
    IFavoriteAccommodationService favoriteAccommodationService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FavoriteAccommodationDTO> getFavoriteAccommodation(@PathVariable("id") Long id) {
        FavoriteAccommodationDTO accommodation = favoriteAccommodationService.findOne(id);
        if (accommodation == null) {
            return new ResponseEntity<FavoriteAccommodationDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<FavoriteAccommodationDTO>(accommodation, HttpStatus.OK);
    }
    //get favorite accommodations for a guest
    @GetMapping(value = "/guest",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<FavoriteAccommodationDTO>> getGuestFavoriteAccommodations(@RequestParam("guestId") Long id) {
        Collection<FavoriteAccommodationDTO>accommodations=favoriteAccommodationService.findAllForGuest(id);
        return new ResponseEntity<>(accommodations, HttpStatus.OK);
    }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FavoriteAccommodationDTO> addFavoriteAccommodation(@RequestBody FavoriteAccommodationDTO accommodation) throws Exception {
        FavoriteAccommodationDTO savedAccommodation = favoriteAccommodationService.create(accommodation);
        return new ResponseEntity<FavoriteAccommodationDTO>(savedAccommodation, HttpStatus.CREATED);
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<FavoriteAccommodationDTO> removeFavoriteAccommodation(@PathVariable("id") Long id) {
        favoriteAccommodationService.delete(id);
        return new ResponseEntity<FavoriteAccommodationDTO>(HttpStatus.NO_CONTENT);
    }
}
