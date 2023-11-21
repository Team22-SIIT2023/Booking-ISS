package com.booking.BookingApp.service.interfaces;

import com.booking.BookingApp.domain.Request;
import com.booking.BookingApp.domain.enums.RequestStatus;

import java.util.Collection;

public interface IRequestService {

    Collection<Request> findAll();

    Request findById(Long id);

    Collection<Request> findByHostId(Long id);

    Collection<Request> findByGuestId(Long id);

    Collection<Request> findByStatus(RequestStatus status);

    Collection<Request> findReservationByGuestId(Long id, RequestStatus status);

    Collection<Request> findWaitingRequest(RequestStatus status);

    Request findByAccommodationId(Long id);

    Request create(Request request);

    Request update(Request request);

    void delete(Long id);
}
