package com.booking.BookingApp.service;

import com.booking.BookingApp.domain.Request;
import com.booking.BookingApp.domain.TimeSlot;
import com.booking.BookingApp.domain.enums.RequestStatus;
import com.booking.BookingApp.service.interfaces.IRequestService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Service
public class RequestService implements IRequestService {
    @Override
    public Collection<Request> findAll(RequestStatus status, Date begin, Date end, String accommodationName) { return data(); }

    @Override
    public Request findById(Long id) {
        return oneRequest();
    }

    @Override
    public Collection<Request> findByHostId(Long id, RequestStatus status) {
        return data();
    }

    @Override
    public Collection<Request> findByGuestId(Long id, RequestStatus status) {return data();}

    @Override
    public Collection<Request> findReservationByGuestId(Long id, RequestStatus status) {
        return data();
    }

    @Override
    public Collection<Request> findWaitingRequest(Long id) {return data();}

    @Override
    public Request findByAccommodationId(Long id) {
        return oneRequest();
    }

    @Override
    public Request create(Request request) {
        return oneRequest();
    }

    @Override
    public Request update(Request requestForUpdate, Request request) {
        return oneRequest();
    }

    @Override
    public void delete(Long id) {}

    public Collection<Request> data() {
        Collection<Request> requestList = new ArrayList<>();

        TimeSlot timeSlot1 = new TimeSlot(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 5));
        TimeSlot timeSlot2 = new TimeSlot(LocalDate.of(2023, 2, 1), LocalDate.of(2023, 2, 7));

        requestList.add(new Request(1L, timeSlot1, 100.0, null, null,5, RequestStatus.CANCELLED));
        requestList.add(new Request(2L, timeSlot2, 150.0, null, null,5, RequestStatus.ACCEPTED));

        return requestList;
    }

    public Request oneRequest() {
        TimeSlot timeSlot1 = new TimeSlot(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 5));
        return new Request(1L, timeSlot1, 100.0, null, null,5, RequestStatus.CANCELLED);
    }
}
