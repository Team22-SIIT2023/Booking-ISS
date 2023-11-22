package com.booking.BookingApp.service.interfaces;

import com.booking.BookingApp.domain.Amenity;
import com.booking.BookingApp.dto.AmenityDTO;

import java.util.Collection;

public interface IAmenityService {

    Collection<AmenityDTO> findAll();

    AmenityDTO findById(Long id);

    AmenityDTO create(AmenityDTO amenity);

    AmenityDTO update(AmenityDTO amenity);

    AmenityDTO delete(Long id);
}
