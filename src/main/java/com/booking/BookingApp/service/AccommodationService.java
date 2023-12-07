package com.booking.BookingApp.service;

import com.booking.BookingApp.domain.Accommodation;
import com.booking.BookingApp.domain.Address;
import com.booking.BookingApp.domain.Amenity;
import com.booking.BookingApp.domain.enums.AccommodationStatus;
import com.booking.BookingApp.domain.enums.AccommodationType;
import com.booking.BookingApp.repository.AccommodationRepository;
import com.booking.BookingApp.service.interfaces.IAccommodationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class AccommodationService implements IAccommodationService {

    @Autowired
    AccommodationRepository accommodationRepository;

    @Override
    public Collection<Accommodation> findAll(Date begin, Date end, int guestNumber, AccommodationType type, double startPrice, double endPrice, AccommodationStatus status, String country, String city, List<String> amenities) {
        return data();
    }
    @Override
    public Accommodation findOne(Long id) {
        ArrayList<Amenity> amenities=new ArrayList<>();
        amenities.add(new Amenity(1L,"pool"));
        amenities.add(new Amenity(1L,"pets"));
        amenities.add(new Amenity(1L,"wifi"));
        return new Accommodation(
                1L, "Hotel ABC", "Boasting a garden and views of inner courtyard, The Gate rooms is a sustainable apartment situated in Novi Sad, 1.9 km from SPENS Sports Centre. It is located 2.8 km from Promenada Shopping Mall and features a shared kitchen. The apartment has family rooms.\n" +
                "\n" +
                "All units are fitted with a flat-screen TV with cable channels, fridge, a kettle, a walk-in shower, a hair dryer and a wardrobe. With a private bathroom, units at the apartment complex also boast free WiFi.",
                new Address("Srbija","Novi Sad","21000","Futoska 14"),
                 2, 4, AccommodationType.HOTEL,
                true, true, null, AccommodationStatus.ACCEPTED,
                3, new ArrayList<>(), amenities, new ArrayList<>()
        );
    }
    @Override
    public Accommodation create(Accommodation accommodation) throws Exception {
        accommodation.setStatus(AccommodationStatus.CREATED);
        return accommodationRepository.save(accommodation);
    }

    @Override
    public Accommodation update(Accommodation accommodation, Accommodation accommodationForUpdate) throws Exception {
        accommodationForUpdate.setStatus(AccommodationStatus.UPDATED);
        accommodationForUpdate.setAutomaticConfirmation(accommodation.isAutomaticConfirmation());
        accommodationForUpdate.setFreeTimeSlots(accommodation.getFreeTimeSlots());
        accommodationForUpdate.setReservationDeadline(accommodation.getReservationDeadline());
        accommodationForUpdate.setPriceList(accommodation.getPriceList());
        accommodationForUpdate.setPricePerGuest(accommodation.isPricePerGuest());
//        return accommodationRepository.save(accommodationForUpdate);

     return new Accommodation(
            1L, "Hotel ABC", "Boasting a garden and views of inner courtyard, The Gate rooms is a sustainable apartment situated in Novi Sad, 1.9 km from SPENS Sports Centre. It is located 2.8 km from Promenada Shopping Mall and features a shared kitchen.",
            new Address("Srbija","Novi Sad","21000","Futoska 14"),
            2, 4, AccommodationType.HOTEL,
            true, true, null, AccommodationStatus.CREATED,
            3, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()
    );
    }

    @Override
    public void delete(Long id) {
        accommodationRepository.deleteById(id);
    }


    public List<Accommodation> data() {
        ArrayList<Amenity> amenities=new ArrayList<>();
        amenities.add(new Amenity(1L,"pool"));
        amenities.add(new Amenity(1L,"pets"));
        amenities.add(new Amenity(1L,"parking"));
        List<Accommodation> accommodationList = new ArrayList<>();
        Accommodation accommodation1 = new Accommodation(
                1L, "Hotel ABC", "Boasting a garden and views of inner courtyard, The Gate rooms is a sustainable apartment situated in Novi Sad, 1.9 km from SPENS Sports Centre. It is located 2.8 km from Promenada Shopping Mall and features a shared kitchen.",
                new Address("Srbija","Novi Sad","21000","Futoska 14"),
                 2, 4, AccommodationType.HOTEL,
                true, true, null, AccommodationStatus.CREATED,
                3, new ArrayList<>(), amenities, new ArrayList<>()
        );

        Accommodation accommodation2 = new Accommodation(
                2L, "Apartment XYZ", "Boasting a garden and views of inner courtyard, The Gate rooms is a sustainable apartment situated in Novi Sad, 1.9 km from SPENS Sports Centre. It is located 2.8 km from Promenada Shopping Mall and features a shared kitchen.",
                new Address("Srbija","Novi Sad","21000","Futoska 14"),
                3, 6, AccommodationType.APARTMENT,
                false, false, null, AccommodationStatus.ACCEPTED,
                5, new ArrayList<>(), amenities, new ArrayList<>()
        );
        accommodationList.add(accommodation1);
        accommodationList.add(accommodation2);
        return accommodationList;
    }
}
