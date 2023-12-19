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

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AccommodationService implements IAccommodationService {

    @Value("${image-path}")
    private String imagesDirPath;

    @Autowired
    AccommodationRepository accommodationRepository;

    @Autowired
    RequestRepository requestRepository;

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
    public Accommodation accept(Accommodation accommodation) {
        accommodation.setStatus(AccommodationStatus.ACCEPTED);
        return accommodationRepository.save(accommodation);
    }

    @Override
    public Accommodation decline(Accommodation accommodation) {
        accommodation.setStatus(AccommodationStatus.DECLINED);
        return accommodationRepository.save(accommodation);
    }

    @Override
    public Collection<Accommodation> findAll(LocalDate begin, LocalDate end, int guestNumber, AccommodationType accommodationType, double startPrice, double endPrice, AccommodationStatus status, String country, String city, List<String> amenities, Integer hostId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Collection<Accommodation> accommodations=new ArrayList<>();
        if(status!=null){
            accommodations = accommodationRepository.findByStatus(status);
        }
        else{
        int size=0;
        if(amenities!=null){
            size=amenities.size();
        }
        if(begin!=null && end!=null){
                accommodations= accommodationRepository.findAccommodationsByCountryTypeGuestNumberTimeRangeAndAmenities(
                        country,
                        city,
                        accommodationType,
                        guestNumber,
                        formatter.format(begin),
                        formatter.format(end),
                        amenities,
                        size,
                        hostId
                );
        }else{
                accommodations= accommodationRepository.findAccommodationsByCountryTypeGuestNumberAndAmenities(
                        country,
                        city,
                        accommodationType,
                        guestNumber,
                        amenities,
                        size,
                        hostId
                );
            }

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
        }
            return accommodations;
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
    public Accommodation update(Accommodation updatedAccommodation) throws Exception {
        System.out.println(updatedAccommodation);
        Long accommodationId = updatedAccommodation.getId();
        Accommodation existingAccommodation = accommodationRepository.findById(accommodationId).orElse(null);

        if (existingAccommodation == null) {
            throw new Exception("Accommodation not found with ID: " + accommodationId);
        }

        existingAccommodation.setName(updatedAccommodation.getName());
        existingAccommodation.setDescription(updatedAccommodation.getDescription());
        existingAccommodation.setAddress(updatedAccommodation.getAddress());
        existingAccommodation.setAutomaticConfirmation(updatedAccommodation.isAutomaticConfirmation());
        existingAccommodation.setMaxGuests(updatedAccommodation.getMaxGuests());
        existingAccommodation.setMinGuests(updatedAccommodation.getMinGuests());
        existingAccommodation.setPricePerGuest(updatedAccommodation.isPricePerGuest());
        existingAccommodation.setReservationDeadline(updatedAccommodation.getReservationDeadline());
        existingAccommodation.setAmenities(updatedAccommodation.getAmenities());
        existingAccommodation.setType(updatedAccommodation.getType());
        existingAccommodation.setStatus(AccommodationStatus.UPDATED);

        System.out.println(existingAccommodation);

        return accommodationRepository.save(existingAccommodation);
//        accommodationForUpdate.setStatus(AccommodationStatus.UPDATED);
//        accommodationForUpdate.setFreeTimeSlots(accommodation.getFreeTimeSlots());
//        accommodationForUpdate.setPriceList(accommodation.getPriceList());
//        return accommodationRepository.save(accommodationForUpdate);

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
            TimeSlot newFreeTimeSlot = new TimeSlot(newTimeSlot.getStartDate(), newTimeSlot.getEndDate(),false);
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
                pricelistItem2.setTimeSlot(new TimeSlot(price.getTimeSlot().getStartDate(), price.getTimeSlot().getEndDate(),false));

                PricelistItem pricelistItem3 = new PricelistItem();
                pricelistItem3.setPrice(pricelist.getPrice());
                pricelistItem3.setTimeSlot(new TimeSlot(price.getTimeSlot().getEndDate(), endDate, false));

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
                pricelistItem2.setTimeSlot(new TimeSlot(price.getTimeSlot().getStartDate(), price.getTimeSlot().getEndDate(), false));
                accommodationForUpdate.getPriceList().add(pricelistItem2);
                check=true;
                break;
            }

            if (startDate.isAfter(price.getTimeSlot().getStartDate()) && endDate.isAfter(price.getTimeSlot().getEndDate()) &&
            price.getTimeSlot().getEndDate().isAfter(startDate)) {
                pricelist.getTimeSlot().setStartDate(price.getTimeSlot().getEndDate());
                PricelistItem pricelistItem2 = new PricelistItem();
                pricelistItem2.setPrice(price.getPrice());
                pricelistItem2.setTimeSlot(new TimeSlot(price.getTimeSlot().getStartDate(), price.getTimeSlot().getEndDate(), false));
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
            pricelistItem.setTimeSlot(new TimeSlot(price.getTimeSlot().getStartDate(), price.getTimeSlot().getEndDate(), false));
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

    public List<String> getImages(Long id) throws IOException {
        Accommodation accommodation = findOne(id);
        List<String> base64Images = new ArrayList<>();

        String directoryPath = StringUtils.cleanPath(imagesDirPath + accommodation.getId());
        File directory = new File(directoryPath);

        if (directory.exists() && directory.isDirectory()) {
            File[] imageFiles = directory.listFiles();

            if (imageFiles != null) {
                for (File imageFile : imageFiles) {
                    if (imageFile.isFile()) {
                        byte[] imageData = Files.readAllBytes(imageFile.toPath());
                        String base64Image = Base64.getEncoder().encodeToString(imageData);
                        base64Images.add(base64Image);
                    }
                }
            }
        }
        return base64Images;
    }


    public List<Accommodation> data() {
        ArrayList<Amenity> amenities=new ArrayList<>();
        amenities.add(new Amenity(1L,"pool", false));
        amenities.add(new Amenity(1L,"pets",false));
        amenities.add(new Amenity(1L,"parking",false));
        List<Accommodation> accommodationList = new ArrayList<>();
        Accommodation accommodation1 = new Accommodation(
                1L, "Hotel ABC", "Boasting a garden and views of inner courtyard, The Gate rooms is a sustainable apartment situated in Novi Sad, 1.9 km from SPENS Sports Centre. It is located 2.8 km from Promenada Shopping Mall and features a shared kitchen.",
                new Address("Srbija","Novi Sad","21000","Futoska 14", false),
                 2, 4, AccommodationType.HOTEL,
                true, true, null, AccommodationStatus.CREATED,
                3, new ArrayList<>(), amenities, new ArrayList<>(), false
        );

        Accommodation accommodation2 = new Accommodation(
                2L, "Apartment XYZ", "Boasting a garden and views of inner courtyard, The Gate rooms is a sustainable apartment situated in Novi Sad, 1.9 km from SPENS Sports Centre. It is located 2.8 km from Promenada Shopping Mall and features a shared kitchen.",
                new Address("Srbija","Novi Sad","21000","Futoska 14", false),
                3, 6, AccommodationType.APARTMENT,
                false, false, null, AccommodationStatus.ACCEPTED,
                5, new ArrayList<>(), amenities, new ArrayList<>(), false
        );
        accommodationList.add(accommodation1);
        accommodationList.add(accommodation2);
        return accommodationList;
    }
}
