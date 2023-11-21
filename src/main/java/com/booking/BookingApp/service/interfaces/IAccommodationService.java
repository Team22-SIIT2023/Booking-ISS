package com.booking.BookingApp.service.interfaces;

import com.booking.BookingApp.domain.Accommodation;
import com.booking.BookingApp.domain.enums.AccommodationType;
import com.booking.BookingApp.domain.enums.Status;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface IAccommodationService {
    Collection<Accommodation>findAll();
    Accommodation findOne(Long id);
    Accommodation create(Accommodation accommodation) throws Exception;

    Accommodation update(Accommodation accommodation) throws Exception;

    void delete(Long id);

    List<Accommodation> findAllForDates(Date begin, Date end);

    List<Accommodation> findAllForGuestNumber(int guestNumber);

    List<Accommodation> findAllForType(AccommodationType type);

    List<Accommodation> findAllForPrice(double startPrice, double endPrice);

    List<Accommodation> findAllForStatus(Status status);

    List<Accommodation> findAllForLocation(String country, String city);

    List<Accommodation> findAllForAmenities(List<String> amenities);
}
