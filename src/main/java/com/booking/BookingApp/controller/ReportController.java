package com.booking.BookingApp.controller;

import com.booking.BookingApp.domain.Accommodation;
import com.booking.BookingApp.domain.Report;
import com.booking.BookingApp.domain.TimeSlot;
import com.booking.BookingApp.dto.TimeSlotDTO;
import com.booking.BookingApp.service.interfaces.IReportService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    private IReportService reportService;

    @GetMapping(value = "/hostAndTimeslot",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Report> getReportByHostAndTimeSlot(
            @RequestParam("hostId") Long hostId,
            @RequestParam("begin") @DateTimeFormat(pattern="yyyy-MM-dd") Date begin,
            @RequestParam("end") @DateTimeFormat(pattern="yyyy-MM-dd") Date end) {
        TimeSlotDTO timeSlot = new TimeSlotDTO
                (begin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                        end.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        Report report=reportService.findOneByHostAndTimeSlot(hostId,timeSlot);
        return new ResponseEntity<Report>(report, HttpStatus.OK);
    };

    @GetMapping(value = "/annual",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Report> getAnnualReportByAccommodationAndHost(
            @RequestParam("hostId") Long hostId,
            @RequestParam("accommodationId") Long accommodationId,
            @RequestParam("year") int year) {
        Report report=reportService.findAnnualByAccommodationAndHost(hostId,accommodationId,year);
        return new ResponseEntity<Report>(report, HttpStatus.OK);
    };

}
