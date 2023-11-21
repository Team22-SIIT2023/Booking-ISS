package com.booking.BookingApp.service.interfaces;

import com.booking.BookingApp.domain.Amenity;

import java.util.Collection;

public interface IAmenityService {

    Collection<Amenity> findAll();

    Amenity findById(Long id);

    Amenity create(Amenity amenity);

    Amenity update(Amenity amenity);

    Amenity delete(Long id);
}
