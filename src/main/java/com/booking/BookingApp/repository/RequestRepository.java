package com.booking.BookingApp.repository;

import com.booking.BookingApp.domain.Request;
import com.booking.BookingApp.domain.enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;

public interface RequestRepository extends JpaRepository<Request, Long> {
    Collection<Request> findByStatus(RequestStatus status);
    Collection<Request> findByStatusAndAccommodation_Name(RequestStatus status, String accommodationName);

    Collection<Request> findByStatusAndAccommodation_NameAndTimeSlot_StartDateLessThanEqualAndTimeSlot_EndDateGreaterThanEqual(RequestStatus status,String accommodationName,LocalDate end,LocalDate begin);
    Collection<Request> findByStatusAndAccommodation_Host_Id(RequestStatus status, Long id);

    Collection<Request> findByStatusAndGuest_Id(RequestStatus status, Long id);

}
