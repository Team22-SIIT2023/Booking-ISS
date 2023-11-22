package com.booking.BookingApp.service;

import com.booking.BookingApp.domain.Report;
import com.booking.BookingApp.domain.TimeSlot;
import com.booking.BookingApp.dto.TimeSlotDTO;
import com.booking.BookingApp.service.interfaces.IReportService;
import org.springframework.stereotype.Service;

@Service
public class ReportService implements IReportService {


    @Override
    public Report findOneByHostAndTimeSlot(Long hostId, TimeSlotDTO timeslot) {
        return null;
    }

    @Override
    public Report findAnnualByAccommodationAndHost(Long hostId, Long accommodationId, int year) {
        return null;
    }
}
