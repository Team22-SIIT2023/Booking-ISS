package com.booking.BookingApp.service;

import com.booking.BookingApp.domain.Accommodation;
import com.booking.BookingApp.domain.enums.AccommodationType;
import com.booking.BookingApp.domain.enums.Status;
import com.booking.BookingApp.dto.AccommodationDTO;
import com.booking.BookingApp.service.interfaces.IAccommodationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class AccommodationService implements IAccommodationService {
    @Override
    public Collection<AccommodationDTO> findAll() {
        return data();
    }

    @Override
    public AccommodationDTO findOne(Long id) {
        return new AccommodationDTO(1L, "Hotel A", "Description A", null, AccommodationType.HOTEL);
    }

    @Override
    public AccommodationDTO create(AccommodationDTO accommodation) throws Exception {
        return null;
    }

    @Override
    public AccommodationDTO update(AccommodationDTO accommodation) throws Exception {
        return null;
    }

    @Override
    public void delete(Long id) {}

    @Override
    public List<AccommodationDTO> findAllForDates(Date begin, Date end) {
        return data();
    }

    @Override
    public List<AccommodationDTO> findAllForGuestNumber(int guestNumber) {
        return data();
    }

    @Override
    public List<AccommodationDTO> findAllForType(AccommodationType type) {
        return data();
    }

    @Override
    public List<AccommodationDTO> findAllForPrice(double startPrice, double endPrice) {
        return data();
    }

    @Override
    public List<AccommodationDTO> findAllForStatus(Status status) {
        return data();
    }

    @Override
    public List<AccommodationDTO> findAllForLocation(String country, String city) {
        return data();
    }

    @Override
    public List<AccommodationDTO> findAllForAmenities(List<String> amenities) {
        return data();
    }

    public List<AccommodationDTO> data() {
        List<AccommodationDTO> accommodationList = new ArrayList<>();

        // Add instances of AccommodationDTO to the list
        accommodationList.add(new AccommodationDTO(1L, "Hotel A", "Description A", null, AccommodationType.HOTEL));
        accommodationList.add(new AccommodationDTO(2L, "Resort B", "Description B", null, AccommodationType.VILLA));
        return accommodationList;
    }
}
