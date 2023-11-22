package com.booking.BookingApp.service.interfaces;

import com.booking.BookingApp.domain.Report;
import com.booking.BookingApp.domain.TimeSlot;
import com.booking.BookingApp.dto.TimeSlotDTO;

import java.util.Collection;

public interface IReportService {

    Report findOneByHostAndTimeSlot(Long hostId, TimeSlotDTO timeslot);
    Report findAnnualByAccommodationAndHost(Long hostId, Long accommodationId,  int year);

}
