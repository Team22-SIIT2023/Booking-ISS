package com.booking.BookingApp.service;

import com.booking.BookingApp.domain.Report;
import com.booking.BookingApp.domain.TimeSlot;
import com.booking.BookingApp.dto.ReportDTO;
import com.booking.BookingApp.dto.TimeSlotDTO;
import com.booking.BookingApp.service.interfaces.IReportService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService implements IReportService {


    @Override
    public Report findOneByHostAndTimeSlot(Long hostId, TimeSlot timeslot) {
        return new Report(1L,4,3);
    }

    @Override
    public Report findAnnualByAccommodation(Long accommodationId, int year) {
        return new Report(1L,4,3);
    }

}
