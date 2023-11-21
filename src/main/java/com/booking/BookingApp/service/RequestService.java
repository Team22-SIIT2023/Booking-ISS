package com.booking.BookingApp.service;

import com.booking.BookingApp.domain.Request;
import com.booking.BookingApp.domain.enums.RequestStatus;
import com.booking.BookingApp.service.interfaces.IRequestService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class RequestService implements IRequestService {
    @Override
    public Collection<Request> findAll() {
        return null;
    }

    @Override
    public Request findById(Long id) {
        return null;
    }

    @Override
    public Collection<Request> findByHostId(Long id) {
        return null;
    }

    @Override
    public Collection<Request> findByGuestId(Long id) {
        return null;
    }

    @Override
    public Collection<Request> findByStatus(RequestStatus status) {
        return null;
    }

    @Override
    public Collection<Request> findReservationByGuestId(Long id, RequestStatus status) {
        return null;
    }

    @Override
    public Collection<Request> findWaitingRequest(RequestStatus status) {return null;}

    @Override
    public Request findByAccommodationId(Long id) {
        return null;
    }

    @Override
    public Request create(Request request) {
        return null;
    }

    @Override
    public Request update(Request request) {
        return null;
    }

    @Override
    public void delete(Long id) {}
}
