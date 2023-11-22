package com.booking.BookingApp.service;

import com.booking.BookingApp.domain.Request;
import com.booking.BookingApp.domain.TimeSlot;
import com.booking.BookingApp.domain.enums.RequestStatus;
import com.booking.BookingApp.dto.RequestDTO;
import com.booking.BookingApp.service.interfaces.IRequestService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class RequestService implements IRequestService {
    @Override
    public Collection<RequestDTO> findAll() {
        return null;
    }

    @Override
    public RequestDTO findById(Long id) {
        return oneRequest();
    }

    @Override
    public Collection<RequestDTO> findByHostId(Long id) {
        return data();
    }

    @Override
    public Collection<RequestDTO> findByGuestId(Long id) {return data();}

    @Override
    public Collection<RequestDTO> findByStatus(RequestStatus status) {
        return data();
    }

    @Override
    public Collection<RequestDTO> findReservationByGuestId(Long id, RequestStatus status) {
        return data();
    }

    @Override
    public Collection<RequestDTO> findWaitingRequest(Long id) {return data();}

    @Override
    public RequestDTO findByAccommodationId(Long id) {
        return oneRequest();
    }

    @Override
    public RequestDTO create(RequestDTO request) {
        return null;
    }

    @Override
    public RequestDTO update(RequestDTO request) {
        return null;
    }

    @Override
    public void delete(Long id) {}

    public Collection<RequestDTO> data() {
        Collection<RequestDTO> requestList = new ArrayList<>();

        TimeSlot timeSlot1 = new TimeSlot(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 5));
        TimeSlot timeSlot2 = new TimeSlot(LocalDate.of(2023, 2, 1), LocalDate.of(2023, 2, 7));

        // Add instances of RequestDTO to the list
        requestList.add(new RequestDTO(1L, timeSlot1, 100.0, null, null, RequestStatus.CANCELLED));
        requestList.add(new RequestDTO(2L, timeSlot2, 150.0, null, null, RequestStatus.ACCEPTED));

        return requestList;
    }

    public RequestDTO oneRequest() {
        TimeSlot timeSlot1 = new TimeSlot(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 5));
        return new RequestDTO(1L, timeSlot1, 100.0, null, null, RequestStatus.CANCELLED);
    }
}
