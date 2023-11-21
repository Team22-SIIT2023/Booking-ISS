package com.booking.BookingApp.service;

import com.booking.BookingApp.domain.Amenity;
import com.booking.BookingApp.service.interfaces.IAmenityService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service

public class AmenityService implements IAmenityService {
    @Override
    public Collection<Amenity> findAll() {return null;}

    @Override
    public Amenity findById(Long id) {return null;}

    @Override
    public Amenity create(Amenity amenity) {return null;}

    @Override
    public Amenity update(Amenity amenity) {return null;}

    @Override
    public Amenity delete(Long id) {return null;}
}
