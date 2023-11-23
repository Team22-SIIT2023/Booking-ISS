package com.booking.BookingApp.service.interfaces;

import com.booking.BookingApp.domain.Report;
import com.booking.BookingApp.domain.TimeSlot;
import com.booking.BookingApp.dto.ReportDTO;
import com.booking.BookingApp.dto.TimeSlotDTO;

import java.util.Collection;

public interface IReportService {

    ReportDTO findOneByHostAndTimeSlot(Long hostId, TimeSlotDTO timeslot);
    ReportDTO findAnnualByAccommodation(Long accommodationId,  int year);

}
