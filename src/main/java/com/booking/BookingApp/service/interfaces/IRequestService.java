package com.booking.BookingApp.service.interfaces;

import com.booking.BookingApp.domain.Request;
import com.booking.BookingApp.domain.enums.RequestStatus;
import com.booking.BookingApp.dto.RequestDTO;

import java.util.Collection;

public interface IRequestService {

    Collection<RequestDTO> findAll();

    RequestDTO findById(Long id);

    Collection<RequestDTO> findByHostId(Long id);

    Collection<RequestDTO> findByGuestId(Long id);

    Collection<RequestDTO> findByStatus(RequestStatus status);

    Collection<RequestDTO> findReservationByGuestId(Long id, RequestStatus status);

    Collection<RequestDTO> findWaitingRequest(Long id);

    RequestDTO findByAccommodationId(Long id);

    RequestDTO create(RequestDTO request);

    RequestDTO update(RequestDTO request);

    void delete(Long id);
}
