package com.booking.BookingApp.repository;

import com.booking.BookingApp.domain.Guest;
import com.booking.BookingApp.domain.Host;
import com.booking.BookingApp.domain.Request;
import com.booking.BookingApp.domain.User;
import com.booking.BookingApp.domain.enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    Collection<Request> findByStatus(RequestStatus status);
    Collection<Request> findByStatusAndAccommodation_Name(RequestStatus status, String accommodationName);

    Collection<Request> findByStatusAndAccommodation_NameAndTimeSlot_StartDateLessThanEqualAndTimeSlot_EndDateGreaterThanEqual(RequestStatus status,String accommodationName,LocalDate end,LocalDate begin);
//    Collection<Request> findByStatusAndAccommodation_Host_Id(RequestStatus status, Long id);
//
//    Collection<Request> findByStatusAndGuest_Id(RequestStatus status, Long id);

    @Query("SELECT r FROM Request r WHERE r.status = 'ACCEPTED' AND r.timeSlot.startDate > :currentDateTime AND r.guest = :guest")
    Collection<Request> findActiveReservationsForGuest(@Param("currentDateTime") LocalDate currentDateTime, @Param("guest") Guest guest);

    @Query("SELECT r FROM Request r JOIN r.accommodation a JOIN a.host h WHERE r.status = 'ACCEPTED' AND r.timeSlot.startDate > :currentDateTime AND h = :host")
    Collection<Request> findActiveReservationsForHost(@Param("currentDateTime") LocalDate currentDateTime, @Param("host") Host host);

    Collection<Request> findByAccommodation_Host(Host host);

    Collection<Request> findByGuest_Id(Long id);
  
    Collection<Request> findByStatusAndAccommodation_Id(RequestStatus status, Long id);

    Collection<Request> findByAccommodation_Host_Id(Long id);

    @Query("SELECT r FROM Request r WHERE " +
            "(:hostId is null or r.accommodation.host.id = :hostId) " +
            "and (:status is null or r.status = :status) " +
            "and (COALESCE(:begin, r.timeSlot.startDate) <= r.timeSlot.endDate) " +
            "and (COALESCE(:end, r.timeSlot.endDate) >= r.timeSlot.startDate) " +
            "and (:accommodationName is null or r.accommodation.name like %:accommodationName%)")
    Collection<Request> findByAccommodation_Host_IdAndStatusAndTimeSlot_StartDateGreaterThanEqualAndTimeSlot_EndDateLessThanEqualAndAccommodation_NameContaining(
            @Param("hostId") Long hostId,
            @Param("status") RequestStatus status,
            @Param("begin") LocalDate begin,
            @Param("end") LocalDate end,
            @Param("accommodationName") String accommodationName
    );
@Query("SELECT r FROM Request r WHERE " +
        "(:guestId is null or r.guest.id = :guestId) " +
        "and (:status is null or r.status = :status) " +
        "and (COALESCE(:begin, r.timeSlot.startDate) <= r.timeSlot.endDate) " +
        "and (COALESCE(:end, r.timeSlot.endDate) >= r.timeSlot.startDate) " +
        "and (:accommodationName is null or r.accommodation.name like %:accommodationName%)")
Collection<Request> findByGuest_IdAndStatusAndTimeSlot_StartDateGreaterThanEqualAndTimeSlot_EndDateLessThanEqualAndAccommodation_NameContaining(
        @Param("guestId") Long guestId,
        @Param("status") RequestStatus status,
        @Param("begin") LocalDate begin,
        @Param("end") LocalDate end,
        @Param("accommodationName") String accommodationName
);



}
