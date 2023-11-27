package com.booking.BookingApp.service;

import com.booking.BookingApp.domain.Accommodation;
import com.booking.BookingApp.domain.Address;
import com.booking.BookingApp.domain.Amenity;
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
        ArrayList<Amenity> amenities=new ArrayList<>();
        amenities.add(new Amenity(1L,"pool",null));
        amenities.add(new Amenity(1L,"pets",null));
        amenities.add(new Amenity(1L,"wifi",null));
        return new Accommodation(
                1L, "Hotel ABC", "Boasting a garden and views of inner courtyard, The Gate rooms is a sustainable apartment situated in Novi Sad, 1.9 km from SPENS Sports Centre. It is located 2.8 km from Promenada Shopping Mall and features a shared kitchen. The apartment has family rooms.\n" +
                "\n" +
                "All units are fitted with a flat-screen TV with cable channels, fridge, a kettle, a walk-in shower, a hair dryer and a wardrobe. With a private bathroom, units at the apartment complex also boast free WiFi.",
                new Address("Srbija","Novi Sad","21000","Futoska 14"),
                new ArrayList<>(), 2, 4, AccommodationType.HOTEL,
                true, true, 1L, Status.ACTIVE,
                3, new ArrayList<>(), amenities, new ArrayList<>()
        );
    }
    @Override
    public Accommodation create(Accommodation accommodation) throws Exception {
        return new Accommodation(
                1L, "Hotel ABC", "A cozy hotel in the city center",
                new Address("Srbija","Novi Sad","21000","Futoska 14"),
                new ArrayList<>(), 2, 4, AccommodationType.HOTEL,
                true, true, 1L, Status.ACTIVE,
                3, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()
        );
    }

    @Override
    public Accommodation update(Accommodation accommodation) throws Exception {
        return new Accommodation(
                1L, "Hotel ABC", "A cozy hotel in the city center",
                new Address("Srbija","Novi Sad","21000","Futoska 14"),
                new ArrayList<>(), 2, 4, AccommodationType.HOTEL,
                true, true, 1L, Status.ACTIVE,
                3, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()
        );
    }

    @Override
    public void delete(Long id) {}


    public List<Accommodation> data() {
        ArrayList<Amenity> amenities=new ArrayList<>();
        amenities.add(new Amenity(1L,"pool",null));
        amenities.add(new Amenity(1L,"pets",null));
        amenities.add(new Amenity(1L,"parking",null));
        List<Accommodation> accommodationList = new ArrayList<>();
        Accommodation accommodation1 = new Accommodation(
                1L, "Hotel ABC", "A cozy hotel in the city center",
                new Address("Srbija","Novi Sad","21000","Futoska 14"),
                new ArrayList<>(), 2, 4, AccommodationType.HOTEL,
                true, true, 1L, Status.ACTIVE,
                3, new ArrayList<>(), amenities, new ArrayList<>()
        );

        Accommodation accommodation2 = new Accommodation(
                2L, "Apartment XYZ", "Spacious apartment with a great view",
                new Address("Srbija","Novi Sad","21000","Futoska 14"),
                new ArrayList<>(), 3, 6, AccommodationType.APARTMENT,
                false, false, 3L, Status.BLOCKED,
                5, new ArrayList<>(), amenities, new ArrayList<>()
        );
        accommodationList.add(accommodation1);
        accommodationList.add(accommodation2);
        return accommodationList;
    }
}
