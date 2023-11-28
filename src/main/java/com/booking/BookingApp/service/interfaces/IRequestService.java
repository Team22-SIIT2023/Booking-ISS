package com.booking.BookingApp.service.interfaces;

import com.booking.BookingApp.domain.Request;
import com.booking.BookingApp.domain.enums.RequestStatus;


import java.util.Collection;
import java.util.Date;

public interface IRequestService {

    Collection<Request> findAll(RequestStatus status, Date begin, Date end, String accommodationName);

    Request findById(Long id);

    Collection<Request> findByHostId(Long id, RequestStatus status);

    Collection<Request> findByGuestId(Long id, RequestStatus status);

    Collection<Request> findReservationByGuestId(Long id, RequestStatus status);

    Collection<Request> findWaitingRequest(Long id);

    Request findByAccommodationId(Long id);

    Request create(Request request);

    Request update(Request request);

    void delete(Long id);
}
