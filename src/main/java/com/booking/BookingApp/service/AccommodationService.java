package com.booking.BookingApp.service;

import com.booking.BookingApp.domain.*;
import com.booking.BookingApp.domain.enums.AccommodationStatus;
import com.booking.BookingApp.domain.enums.AccommodationType;
import com.booking.BookingApp.domain.enums.RequestStatus;
import com.booking.BookingApp.dto.PricelistItemDTO;
import com.booking.BookingApp.dto.TimeSlotDTO;
import com.booking.BookingApp.repository.AccommodationRepository;
import com.booking.BookingApp.repository.RequestRepository;
import com.booking.BookingApp.service.interfaces.IAccommodationService;
import com.booking.BookingApp.utils.ImageUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class AccommodationService implements IAccommodationService {

    @Value("${image-path}")
    private String imagesDirPath;

    @Autowired
    AccommodationRepository accommodationRepository;

    @Autowired
    RequestRepository requestRepository;

    @Override
    public Collection<Accommodation> findAll(Date begin, Date end, int guestNumber, AccommodationType type, double startPrice, double endPrice, AccommodationStatus status, String country, String city, List<String> amenities) {
        return accommodationRepository.findAll();
        //needs filters!
    }

    @Override
    public Accommodation findOne(Long id) {
        return accommodationRepository.findById(id).orElse(null);
    }

    @Override
    public Accommodation create(Accommodation accommodation) throws Exception {
        accommodation.setStatus(AccommodationStatus.CREATED);
        return accommodationRepository.save(accommodation);
    }

    @Override
    public Accommodation update(Accommodation accommodation, Accommodation accommodationForUpdate) throws Exception {
        accommodationForUpdate.setStatus(AccommodationStatus.UPDATED);
        accommodationForUpdate.setFreeTimeSlots(accommodation.getFreeTimeSlots());
        accommodationForUpdate.setPriceList(accommodation.getPriceList());
        return accommodationRepository.save(accommodationForUpdate);
    }

    @Override
    public Accommodation editAccommodationFreeTimeSlots(TimeSlotDTO newTimeSlot, Accommodation accommodationForUpdate) {

        if (reservationOverlaps(newTimeSlot, accommodationForUpdate.getId())) {
            return null;
        }
        boolean check = false;

        Collection<TimeSlot> timeSlots = accommodationForUpdate.getFreeTimeSlots();
        for (TimeSlot timeSlot : timeSlots) {
            if (timeSlot.getStartDate().isBefore(newTimeSlot.getStartDate()) && timeSlot.getEndDate().isAfter(newTimeSlot.getEndDate())) {
                timeSlot.setStartDate(newTimeSlot.getStartDate());
                timeSlot.setEndDate(newTimeSlot.getEndDate());
                check=true;
                break;
            }

            if (timeSlot.getStartDate().isAfter(newTimeSlot.getStartDate()) && timeSlot.getEndDate().isAfter(newTimeSlot.getEndDate()) && timeSlot.getStartDate().isBefore(newTimeSlot.getEndDate())) {
                timeSlot.setStartDate(newTimeSlot.getStartDate());
                timeSlot.setEndDate(newTimeSlot.getEndDate());
                check=true;
                break;
            }

            if (timeSlot.getStartDate().isBefore(newTimeSlot.getStartDate()) && timeSlot.getEndDate().isBefore(newTimeSlot.getEndDate()) && timeSlot.getEndDate().isAfter(newTimeSlot.getStartDate())) {
                timeSlot.setStartDate(newTimeSlot.getStartDate());
                timeSlot.setEndDate(newTimeSlot.getEndDate());
                check=true;
                break;
            }

            if (timeSlot.getStartDate().isAfter(newTimeSlot.getStartDate()) && timeSlot.getEndDate().isBefore(newTimeSlot.getEndDate())) {
                timeSlot.setStartDate(newTimeSlot.getStartDate());
                timeSlot.setEndDate(newTimeSlot.getEndDate());
                check=true;
                break;
            }
        }
        if (!check) {
            TimeSlot newFreeTimeSlot = new TimeSlot(newTimeSlot.getStartDate(), newTimeSlot.getEndDate());
            accommodationForUpdate.getFreeTimeSlots().add(newFreeTimeSlot);
        }
        return accommodationRepository.save(accommodationForUpdate);
    }


    public boolean reservationOverlaps(TimeSlotDTO timslot, Long accommodationId) {
        for (Request request : requestRepository.findByStatusAndAccommodation_Id(RequestStatus.ACCEPTED, accommodationId)) {
            LocalDate requestStart = request.getTimeSlot().getStartDate();
            LocalDate requestEnd = request.getTimeSlot().getEndDate();
            if (requestStart.isAfter(timslot.getStartDate()) && requestEnd.isBefore(timslot.getEndDate())) {
                return true;
            }
            if (requestStart.isBefore(timslot.getStartDate()) && requestEnd.isBefore(timslot.getEndDate()) &&
                timslot.getStartDate().isBefore(requestEnd)) {
                return true;
            }
            if (requestStart.isAfter(timslot.getStartDate()) && requestEnd.isAfter(timslot.getEndDate()) &&
            requestStart.isBefore(timslot.getEndDate())) {
                return true;
            }
            if (requestStart.isBefore(timslot.getStartDate()) && requestEnd.isAfter(timslot.getEndDate())) {
                return true;
            }
        }
        return false;
    }

//            for (TimeSlot ts : accommodation.getFreeTimeSlots()) {
//
//                //imamo jedan veci timeslot i onda se doda jedan manji unutar jel se prekida na kraju malog?
//                if (timeSlot.getStartDate().isBefore(ts.getStartDate()) && timeSlot.getEndDate().isAfter(ts.getEndDate())) {
//                    timeSlot.setEndDate(ts.getEndDate());
//                }
//
//                if (timeSlot.getStartDate().isBefore(ts.getStartDate()) && timeSlot.getEndDate().isBefore(ts.getEndDate())) {
//                    timeSlot.setEndDate(ts.getEndDate());
//                }
//
//                if (timeSlot.getStartDate().isAfter(ts.getStartDate()) && timeSlot.getEndDate().isAfter(ts.getEndDate())) {
//                    timeSlot.setStartDate(ts.getStartDate());
//                    //da li kraj da bude kraj novog (ts) ili starog (timeslot)???
//                }
//            }


    @Override
    public void delete(Long id) {
        accommodationRepository.deleteById(id);
    }

    @Override
    public Accommodation editAccommodationPricelistItem(PricelistItemDTO price, Accommodation accommodationForUpdate) {
        boolean check = false;
        System.out.println(accommodationForUpdate);
        for (PricelistItem pricelist : accommodationForUpdate.getPriceList()) {
            LocalDate startDate = pricelist.getTimeSlot().getStartDate();
            LocalDate endDate = pricelist.getTimeSlot().getEndDate();

            // startdate????????????????????????????
            if (startDate.isBefore(price.getTimeSlot().getStartDate()) && endDate.isAfter(price.getTimeSlot().getEndDate())) {
                pricelist.getTimeSlot().setEndDate(price.getTimeSlot().getStartDate());
                PricelistItem pricelistItem2 = new PricelistItem();
                pricelistItem2.setPrice(price.getPrice());
                pricelistItem2.setTimeSlot(new TimeSlot(price.getTimeSlot().getStartDate(), price.getTimeSlot().getEndDate()));

                PricelistItem pricelistItem3 = new PricelistItem();
                pricelistItem3.setPrice(pricelist.getPrice());
                pricelistItem3.setTimeSlot(new TimeSlot(price.getTimeSlot().getEndDate(), endDate));

                accommodationForUpdate.getPriceList().add(pricelistItem2);
                accommodationForUpdate.getPriceList().add(pricelistItem3);
                check=true;
                break;
            }

            if (startDate.isBefore(price.getTimeSlot().getStartDate()) && endDate.isBefore(price.getTimeSlot().getEndDate())
                    && endDate.isAfter(price.getTimeSlot().getStartDate())) {
                pricelist.getTimeSlot().setEndDate(price.getTimeSlot().getStartDate());
                PricelistItem pricelistItem2 = new PricelistItem();
                pricelistItem2.setPrice(price.getPrice());
                pricelistItem2.setTimeSlot(new TimeSlot(price.getTimeSlot().getStartDate(), price.getTimeSlot().getEndDate()));
                accommodationForUpdate.getPriceList().add(pricelistItem2);
                check=true;
                break;
            }

            if (startDate.isAfter(price.getTimeSlot().getStartDate()) && endDate.isAfter(price.getTimeSlot().getEndDate()) &&
            price.getTimeSlot().getEndDate().isAfter(startDate)) {
                pricelist.getTimeSlot().setStartDate(price.getTimeSlot().getEndDate());
                PricelistItem pricelistItem2 = new PricelistItem();
                pricelistItem2.setPrice(price.getPrice());
                pricelistItem2.setTimeSlot(new TimeSlot(price.getTimeSlot().getStartDate(), price.getTimeSlot().getEndDate()));
                accommodationForUpdate.getPriceList().add(pricelistItem2);
                check=true;
                break;
            }
            // proveritiii
            if (startDate.isAfter(price.getTimeSlot().getStartDate()) && endDate.isBefore(price.getTimeSlot().getEndDate())) {
                pricelist.setPrice(price.getPrice());
                pricelist.getTimeSlot().setStartDate(price.getTimeSlot().getStartDate());
                pricelist.getTimeSlot().setEndDate(price.getTimeSlot().getEndDate());
                check=true;
                break;
            }
        }
        if (!check) {
            PricelistItem pricelistItem = new PricelistItem();
            pricelistItem.setPrice(price.getPrice());
            pricelistItem.setTimeSlot(new TimeSlot(price.getTimeSlot().getStartDate(), price.getTimeSlot().getEndDate()));
            accommodationForUpdate.getPriceList().add(pricelistItem);
        }
        return accommodationRepository.save(accommodationForUpdate);
    }

    public void uploadImage(Long id, MultipartFile image) throws IOException {
        Accommodation accommodation = findOne(id);

        String fileName = StringUtils.cleanPath(image.getOriginalFilename());
        String uploadDir = StringUtils.cleanPath(imagesDirPath + accommodation.getId());

        System.out.println(uploadDir);

        ImageUploadUtil.saveImage(uploadDir, fileName, image);

        accommodation.setImage(fileName);
        accommodationRepository.save(accommodation);
    }

    @Override
    public byte[] getImages(Long id) throws IOException {
        return new byte[0];
    }

//    public byte[] getImages(Long id) throws IOException {
//        Accommodation accommodation = findOne(id);
//
//        String imagePath = StringUtils.cleanPath(imagesDirPath + accommodation.getId() + "/" + accommodation.getProfilePicture());
//        File file = new File(imagePath);
//
//        return Files.readAllBytes(file.toPath());
//    }


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
