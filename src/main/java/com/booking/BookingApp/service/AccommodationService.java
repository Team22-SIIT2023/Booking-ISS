package com.booking.BookingApp.service;

import com.booking.BookingApp.domain.Accommodation;
import com.booking.BookingApp.domain.enums.AccommodationType;
import com.booking.BookingApp.domain.enums.Status;
import com.booking.BookingApp.service.interfaces.IAccommodationService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class AccommodationService implements IAccommodationService {
    @Override
    public Collection<Accommodation> findAll() {
        return null;
    }

    @Override
    public Accommodation findOne(Long id) {
        return null;
    }

    @Override
    public Accommodation create(Accommodation accommodation) throws Exception {
        return null;
    }

    @Override
    public Accommodation update(Accommodation accommodation) throws Exception {
        return null;
    }

    @Override
    public void delete(Long id) {}

    @Override
    public List<Accommodation> findAllForDates(Date begin, Date end) {
        return null;
    }

    @Override
    public List<Accommodation> findAllForGuestNumber(int guestNumber) {
        return null;
    }

    @Override
    public List<Accommodation> findAllForType(AccommodationType type) {
        return null;
    }

    @Override
    public List<Accommodation> findAllForPrice(double startPrice, double endPrice) {
        return null;
    }

    @Override
    public List<Accommodation> findAllForStatus(Status status) {
        return null;
    }

    @Override
    public List<Accommodation> findAllForLocation(String country, String city) {
        return null;
    }

    @Override
    public List<Accommodation> findAllForAmenities(List<String> amenities) {
        return null;
    }

}
