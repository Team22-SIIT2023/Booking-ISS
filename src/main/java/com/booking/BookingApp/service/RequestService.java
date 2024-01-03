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
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class RequestService implements IRequestService {
    @Autowired
    RequestRepository requestRepository;

    @Override
    public Collection<Request> findAll(RequestStatus status, LocalDate begin, LocalDate end, String accommodationName) {
        if(status!=null){
            return requestRepository.findByStatus(status);
        }
        return requestRepository.findAll();

    }
    @Override
    public Request findById(Long id) {
        return requestRepository.findById(id).orElse(null);
    }

    @Override
    public Collection<Request> findByHostId(Long id,RequestStatus status, LocalDate begin, LocalDate end, String accommodationName) {
        return requestRepository.findAllForHost(id, status, begin, end, accommodationName);
    }
    @Override
    public Collection<Request> findByHost(Long id) {
        return requestRepository.findByAccommodation_Host_Id(id);
    }

    public Collection<Request> findByGuestId(Long id, RequestStatus status, LocalDate begin, LocalDate end, String accommodationName) {
        return requestRepository.findAllForGuest(id, status,  begin, end, accommodationName);
    }

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
    @Transactional
    public void delete(Long id) {
        Request request = requestRepository.findById(id).orElse(null);
        if (request != null) {
            request.setDeleted(true);
            requestRepository.save(request);
        }
    }

    @Override
    public int findCancellations(Long id) {
        Collection<Request> requests=requestRepository.findAllForGuest(id,RequestStatus.CANCELLED,null,null,null);
        return requests.size();
    }
    @Override
    public Collection<Request> findReservationsByYear(String accommodationName,int year) {
        return requestRepository.findAllByAccommodationNameAndYear(accommodationName,year);
    }

}
