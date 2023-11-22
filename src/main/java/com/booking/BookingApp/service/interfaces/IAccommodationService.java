package com.booking.BookingApp.service.interfaces;

import com.booking.BookingApp.domain.Accommodation;
import com.booking.BookingApp.domain.enums.AccommodationType;
import com.booking.BookingApp.domain.enums.Status;
import com.booking.BookingApp.dto.AccommodationDTO;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface IAccommodationService {
    Collection<AccommodationDTO> findAll();
    AccommodationDTO findOne(Long id);
    AccommodationDTO create(AccommodationDTO accommodation) throws Exception;

    AccommodationDTO update(AccommodationDTO accommodation) throws Exception;

    void delete(Long id);

    List<AccommodationDTO> findAllForDates(Date begin, Date end);

    List<AccommodationDTO> findAllForGuestNumber(int guestNumber);

    List<AccommodationDTO> findAllForType(AccommodationType type);

    List<AccommodationDTO> findAllForPrice(double startPrice, double endPrice);

    List<AccommodationDTO> findAllForStatus(Status status);

    List<AccommodationDTO> findAllForLocation(String country, String city);

    List<AccommodationDTO> findAllForAmenities(List<String> amenities);
}
