package com.booking.BookingApp.service;

import com.booking.BookingApp.domain.Amenity;
import com.booking.BookingApp.dto.AmenityDTO;
import com.booking.BookingApp.service.interfaces.IAmenityService;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;

@Service

public class AmenityService implements IAmenityService {
    @Override
    public Collection<Amenity> findAll() {return data();}

    @Override
    public Amenity findById(Long id) {
        return new Amenity(1L, "Swimming Pool", null);
    }

    @Override
    public Amenity create(Amenity amenity) {return new Amenity(1L, "Swimming Pool", null);}

    @Override
    public Amenity update(Amenity amenity) {return new Amenity(1L, "Swimming Pool", null);}

    @Override
    public void delete(Long id) {}

    public Collection<Amenity> data() {
        Collection<Amenity> amenityList = new ArrayList<>();

        // Add instances of Amenity to the list
        amenityList.add(new Amenity(1L, "Swimming Pool", null));
        amenityList.add(new Amenity(2L, "Gym", null));
        amenityList.add(new Amenity(3L, "WiFi", null));
        amenityList.add(new Amenity(3L, "WiFi", null));

        return amenityList;
    }
}
