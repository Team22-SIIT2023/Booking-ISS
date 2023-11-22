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
    public Collection<AmenityDTO> findAll() {return data();}

    @Override
    public AmenityDTO findById(Long id) {
        return new AmenityDTO(1L, "Swimming Pool", null);
    }

    @Override
    public AmenityDTO create(AmenityDTO amenity) {return null;}

    @Override
    public AmenityDTO update(AmenityDTO amenity) {return null;}

    @Override
    public AmenityDTO delete(Long id) {return null;}

    public Collection<AmenityDTO> data() {
        Collection<AmenityDTO> amenityList = new ArrayList<>();

        // Add instances of AmenityDTO to the list
        amenityList.add(new AmenityDTO(1L, "Swimming Pool", null));
        amenityList.add(new AmenityDTO(2L, "Gym", null));
        amenityList.add(new AmenityDTO(3L, "WiFi", null));
        amenityList.add(new AmenityDTO(3L, "WiFi", null));

        return amenityList;
    }
}
