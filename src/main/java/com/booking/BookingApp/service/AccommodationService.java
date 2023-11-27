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
    public Collection<Accommodation> findAll(Date begin, Date end, int guestNumber, AccommodationType type, double startPrice, double endPrice, Status status, String country, String city, List<String> amenities) {
        return data();
    }
    @Override
    public Accommodation findOne(Long id) {
        return new Accommodation(
                1L, "Hotel ABC", "A cozy hotel in the city center",
                null,
                new ArrayList<>(), 2, 4, AccommodationType.HOTEL,
                true, true, 1L, Status.ACTIVE,
                3, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()
        );
    }
    @Override
    public Accommodation create(Accommodation accommodation) throws Exception {
        return new Accommodation(
                1L, "Hotel ABC", "A cozy hotel in the city center",
                null,
                new ArrayList<>(), 2, 4, AccommodationType.HOTEL,
                true, true, 1L, Status.ACTIVE,
                3, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()
        );
    }

    @Override
    public Accommodation update(Accommodation accommodation) throws Exception {
        return new Accommodation(
                1L, "Hotel ABC", "A cozy hotel in the city center",
                null,
                new ArrayList<>(), 2, 4, AccommodationType.HOTEL,
                true, true, 1L, Status.ACTIVE,
                3, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()
        );
    }

    @Override
    public void delete(Long id) {}


    public List<Accommodation> data() {
        List<Accommodation> accommodationList = new ArrayList<>();
        Accommodation accommodation1 = new Accommodation(
                1L, "Hotel ABC", "A cozy hotel in the city center",
                null,
                new ArrayList<>(), 2, 4, AccommodationType.HOTEL,
                true, true, 1L, Status.ACTIVE,
                3, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()
        );

        Accommodation accommodation2 = new Accommodation(
                2L, "Apartment XYZ", "Spacious apartment with a great view",
                null,
                new ArrayList<>(), 3, 6, AccommodationType.APARTMENT,
                false, false, 1L, Status.BLOCKED,
                5, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()
        );
        accommodationList.add(accommodation1);
        accommodationList.add(accommodation2);
        return accommodationList;
    }
}
