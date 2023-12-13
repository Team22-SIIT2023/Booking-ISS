package com.booking.BookingApp.service;

import com.booking.BookingApp.domain.*;
import com.booking.BookingApp.domain.enums.AccommodationStatus;
import com.booking.BookingApp.domain.enums.AccommodationType;
import com.booking.BookingApp.repository.AccommodationRepository;
import com.booking.BookingApp.service.interfaces.IAccommodationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccommodationService implements IAccommodationService {

    @Autowired
    AccommodationRepository accommodationRepository;

    @Override
    public double calculatePriceForAccommodation(Long id, int guestNumber, Date begin, Date end) {
        Accommodation accommodation=findOne(id);
        double price = 0;

        long timeDifference=end.getTime()-begin.getTime();
        double days=Math.floor(timeDifference / (1000 * 60 * 60 * 24));
        int counter=0;

        boolean pricePerGuest = accommodation.isPricePerGuest();
        Collection<PricelistItem> priceList = accommodation.getPriceList();

        if (priceList != null) {
            for (PricelistItem item : priceList) {
                long intersectionStart = Math.max(begin.getTime(), java.sql.Date.valueOf(item.getTimeSlot().getStartDate()).getTime());
                long intersectionEnd = Math.min(end.getTime(), java.sql.Date.valueOf(item.getTimeSlot().getEndDate()).getTime());

                if (intersectionStart < intersectionEnd) {
                    long daysInIntersection = (intersectionEnd - intersectionStart) / (1000 * 60 * 60 * 24);
                    price +=daysInIntersection * item.getPrice();
                    counter+=daysInIntersection;
                }
            }
        }
        if(counter<days){
            return 0;
        }
        if(pricePerGuest){
           return price*guestNumber;
        }
        return price;
    }

    @Override
    public Collection<Accommodation> findAll(LocalDate begin, LocalDate end, int guestNumber, AccommodationType accommodationType, double startPrice, double endPrice, AccommodationStatus status, String country, String city, List<String> amenities) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedBegin = null;
        String formattedEnd = null;
        int size=0;
            if(begin!=null && end!=null){
                formattedBegin = formatter.format(begin);
                formattedEnd = formatter.format(end);
            }
            if(amenities!=null){
                size=amenities.size();
            }
        System.out.println(formattedBegin);
            System.out.println(formattedEnd);
        Collection<Accommodation> accommodations= accommodationRepository.findAccommodationsByCountryTypeGuestNumberTimeRangeAndAmenities(
                    country,
                    accommodationType,
                    guestNumber,
                    formattedBegin,
                    formattedEnd,
                    amenities,
                    size
                    );
            if(endPrice>0 && startPrice>0 && begin!=null && end!=null && guestNumber>0){
                Date beginDate = Date.from(begin.atStartOfDay(ZoneId.systemDefault()).toInstant());
                Date endDate = Date.from(end.atStartOfDay(ZoneId.systemDefault()).toInstant());
                return accommodations.stream()
                        .filter(accommodation ->
                                calculatePriceForAccommodation(accommodation.getId(),
                                        guestNumber,beginDate,endDate) >= startPrice &&
                                        calculatePriceForAccommodation(accommodation.getId(),guestNumber,beginDate,endDate)<= endPrice)
                        .collect(Collectors.toList());
            }
            return accommodations;
    }
    @Override
    public Accommodation findOne(Long id) {
        return accommodationRepository.findById(id).orElse(null);
//        ArrayList<Amenity> amenities=new ArrayList<>();
//        amenities.add(new Amenity(1L,"pool"));
//        amenities.add(new Amenity(1L,"pets"));
//        amenities.add(new Amenity(1L,"wifi"));
//        return new Accommodation(
//                1L, "Hotel ABC", "Boasting a garden and views of inner courtyard, The Gate rooms is a sustainable apartment situated in Novi Sad, 1.9 km from SPENS Sports Centre. It is located 2.8 km from Promenada Shopping Mall and features a shared kitchen. The apartment has family rooms.\n" +
//                "\n" +
//                "All units are fitted with a flat-screen TV with cable channels, fridge, a kettle, a walk-in shower, a hair dryer and a wardrobe. With a private bathroom, units at the apartment complex also boast free WiFi.",
//                new Address("Srbija","Novi Sad","21000","Futoska 14"),
//                 2, 4, AccommodationType.HOTEL,
//                true, true, null, AccommodationStatus.ACCEPTED,
//                3, new ArrayList<>(), amenities, new ArrayList<>()
//        );
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
