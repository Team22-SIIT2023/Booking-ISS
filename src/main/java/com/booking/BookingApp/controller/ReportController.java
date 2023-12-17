package com.booking.BookingApp.controller;

import com.booking.BookingApp.domain.Report;
import com.booking.BookingApp.domain.TimeSlot;
import com.booking.BookingApp.dto.ReportDTO;
import com.booking.BookingApp.dto.TimeSlotDTO;
import com.booking.BookingApp.mapper.ReportDTOMapper;
import com.booking.BookingApp.service.interfaces.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.util.Date;

@CrossOrigin
@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private IReportService reportService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReportDTO> getReportByHostAndTimeSlot(
            @RequestParam("hostId") Long hostId,
            @RequestParam("begin") @DateTimeFormat(pattern="yyyy-MM-dd") Date begin,
            @RequestParam("end") @DateTimeFormat(pattern="yyyy-MM-dd") Date end) {
        TimeSlot timeSlot = new TimeSlot(begin.toInstant().atZone
                (ZoneId.systemDefault()).toLocalDate(),
                end.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),false);
        Report report=reportService.findOneByHostAndTimeSlot(hostId,timeSlot);
        return new ResponseEntity<ReportDTO>(ReportDTOMapper.fromReportToDTO(report), HttpStatus.OK);
    };

    @GetMapping(value = "/annual",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReportDTO> getAnnualReportByAccommodation(
            @RequestParam("accommodationId") Long accommodationId,
            @RequestParam("year") int year) {
        Report report=reportService.findAnnualByAccommodation(accommodationId,year);
        return new ResponseEntity<ReportDTO>(ReportDTOMapper.fromReportToDTO(report), HttpStatus.OK);
    };

}
