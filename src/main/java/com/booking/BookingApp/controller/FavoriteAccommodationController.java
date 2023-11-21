package com.booking.BookingApp.controller;

import com.booking.BookingApp.domain.FavoriteAccommodation;
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
    public ResponseEntity<FavoriteAccommodation> getFavoriteAccommodation(@PathVariable("id") Long id) {
        FavoriteAccommodation accommodation = favoriteAccommodationService.findOne(id);
        if (accommodation == null) {
            return new ResponseEntity<FavoriteAccommodation>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<FavoriteAccommodation>(accommodation, HttpStatus.OK);
    }
    //get favorite accommodations for a guest
    @GetMapping(value = "/guest",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<FavoriteAccommodation>> getGuestFavoriteAccommodation(@RequestParam("guestId") Long id) {
        Collection<FavoriteAccommodation>accommodations=favoriteAccommodationService.findAllForGuest(id);
        return new ResponseEntity<>(accommodations, HttpStatus.OK);
    }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FavoriteAccommodation> addFavoriteAccommodation(@RequestBody FavoriteAccommodation accommodation) throws Exception {
        FavoriteAccommodation savedAccommodation = favoriteAccommodationService.create(accommodation);
        return new ResponseEntity<FavoriteAccommodation>(savedAccommodation, HttpStatus.CREATED);
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<FavoriteAccommodation> removeFavoriteAccommodation(@PathVariable("id") Long id) {
        favoriteAccommodationService.delete(id);
        return new ResponseEntity<FavoriteAccommodation>(HttpStatus.NO_CONTENT);
    }
}
