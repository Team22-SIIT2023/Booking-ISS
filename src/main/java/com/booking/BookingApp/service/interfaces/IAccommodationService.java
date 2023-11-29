package com.booking.BookingApp.service.interfaces;

import com.booking.BookingApp.domain.Accommodation;
import com.booking.BookingApp.domain.enums.AccommodationStatus;
import com.booking.BookingApp.domain.enums.AccommodationType;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface IAccommodationService {
    Collection<Accommodation> findAll(Date begin, Date end, int guestNumber, AccommodationType type, double startPrice, double endPrice, AccommodationStatus status, String country, String city, List<String> amenities);

    Accommodation findOne(Long id);
    Accommodation create(Accommodation accommodation) throws Exception;

    Accommodation update(Accommodation accommodation, Accommodation accommodationForUpdate) throws Exception;

    void delete(Long id);
}
