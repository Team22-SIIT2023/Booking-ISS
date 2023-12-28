package com.booking.BookingApp.service;

import com.booking.BookingApp.domain.Accommodation;
import com.booking.BookingApp.domain.Address;
import com.booking.BookingApp.domain.Request;
import com.booking.BookingApp.domain.TimeSlot;
import com.booking.BookingApp.domain.enums.AccommodationStatus;
import com.booking.BookingApp.domain.enums.AccommodationType;
import com.booking.BookingApp.domain.enums.RequestStatus;
import com.booking.BookingApp.repository.RequestRepository;
import com.booking.BookingApp.service.interfaces.IRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class RequestService implements IRequestService {
    @Autowired
    RequestRepository requestRepository;
    @Override
    public Collection<Request> findAll(RequestStatus status, LocalDate begin, LocalDate end, String accommodationName) {

        if (status != null && accommodationName!=null && begin!=null && end!=null) {
            return requestRepository.findByStatusAndAccommodation_NameAndTimeSlot_StartDateLessThanEqualAndTimeSlot_EndDateGreaterThanEqual(
                    status, accommodationName, end, begin);
        }else if (status!=null && accommodationName!=null) {
            return requestRepository.findByStatusAndAccommodation_Name(status,accommodationName);
        }else if(status!=null){
            return requestRepository.findByStatus(status);
        }
        return requestRepository.findAll();

    }
    @Override
    public Request findById(Long id) {
        return requestRepository.findById(id).orElse(null);
    }

    @Override
    public Collection<Request> findByHostId(Long id, RequestStatus status) {
        return requestRepository.findByStatusAndAccommodation_Host_Id(status,id);
    }

    @Override
    public Collection<Request> findByHost(Long id) {
        return requestRepository.findByAccommodation_Host_Id(id);
    }

    @Override
    public Collection<Request> findByGuestId(Long id, RequestStatus status) {
        return requestRepository.findByStatusAndGuest_Id(status,id);
    }

//    @Override
//    public Collection<Request> findReservationByGuestId(Long id, RequestStatus status) {
//        return data();
//    }

//    @Override
//    public Collection<Request> findWaitingRequest(Long id) {return data();}

//    @Override
//    public Request findByAccommodationId(Long id) {
//        return oneRequest();
//    }

    @Override
    public Request create(Request request) throws Exception{

        return requestRepository.save(request);
    }

    @Override
    public Request update(Request requestForUpdate, Request request) {
        requestForUpdate.setAccommodation(request.getAccommodation());
        requestForUpdate.setTimeSlot(request.getTimeSlot());
        requestForUpdate.setGuestNumber(request.getGuestNumber());
        requestForUpdate.setStatus(request.getStatus());
        requestForUpdate.setPrice(request.getPrice());
        requestForUpdate.setGuest(request.getGuest());
        return requestRepository.save(requestForUpdate);
    }

    @Override
    public void delete(Long id) {
        requestRepository.deleteById(id);
    }

//    public Collection<Request> data() {
//        Collection<Request> requestList = new ArrayList<>();
//        Accommodation accommodation1 = new Accommodation(
//                1L, "Hotel ABC", "Boasting a garden and views of inner courtyard, The Gate rooms is a sustainable apartment situated in Novi Sad, 1.9 km from SPENS Sports Centre. It is located 2.8 km from Promenada Shopping Mall and features a shared kitchen.",
//                new Address("Srbija","Novi Sad","21000","Futoska 14"),
//                2, 4, AccommodationType.HOTEL,
//                true, true, null, AccommodationStatus.CREATED,
//                3, new ArrayList<>(), null, new ArrayList<>()
//        );
//        TimeSlot timeSlot1 = new TimeSlot(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 5));
//        TimeSlot timeSlot2 = new TimeSlot(LocalDate.of(2023, 2, 1), LocalDate.of(2023, 2, 7));
//
//        requestList.add(new Request(1L, timeSlot1, 100.0, null, accommodation1,5, RequestStatus.CANCELLED));
//        requestList.add(new Request(1L, timeSlot1, 100.0, null, accommodation1,5, RequestStatus.CANCELLED));
//        requestList.add(new Request(1L, timeSlot1, 100.0, null, accommodation1,5, RequestStatus.CANCELLED));
//        requestList.add(new Request(2L, timeSlot1, 130.0, null, accommodation1,5, RequestStatus.WAITING));
//        requestList.add(new Request(3L, timeSlot2, 90.0, null, accommodation1,5, RequestStatus.ACCEPTED));
//        requestList.add(new Request(4L, timeSlot2, 150.0, null, accommodation1,5, RequestStatus.ACCEPTED));
//
//        return requestList;
//    }
//
//    public Request oneRequest() {
//        TimeSlot timeSlot1 = new TimeSlot(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 5));
//        return new Request(1L, timeSlot1, 100.0, null, null,5, RequestStatus.CANCELLED);
//    }
}
