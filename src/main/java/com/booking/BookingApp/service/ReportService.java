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
    public ReportDTO findOneByHostAndTimeSlot(Long hostId, TimeSlotDTO timeslot) {
        return new ReportDTO(1L,4,3);
    }

    @Override
    public ReportDTO findAnnualByAccommodation(Long accommodationId, int year) {
        return new ReportDTO(1L,4,3);
    }

}
