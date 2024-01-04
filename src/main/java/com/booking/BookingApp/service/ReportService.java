package com.booking.BookingApp.service;

import com.booking.BookingApp.domain.Accommodation;
import com.booking.BookingApp.domain.Report;
import com.booking.BookingApp.domain.Request;
import com.booking.BookingApp.domain.TimeSlot;
import com.booking.BookingApp.domain.enums.RequestStatus;
import com.booking.BookingApp.service.interfaces.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class ReportService implements IReportService {

    @Autowired
    RequestService requestService;
    @Autowired
    AccommodationService accommodationService;


    @Override
    public Collection<Report> findAllByTimeSlot(int hostId,TimeSlot timeslot) {
        Collection<Report>reports=new ArrayList<>();
        Collection<Accommodation>hostAccommodations=accommodationService.findAll(null,null,
                0,null,0,0,null,null,null,null,
                hostId);
        for(Accommodation accommodation:hostAccommodations){
            Collection<Request>reservations=requestService.findByHostId(Long.valueOf(hostId),RequestStatus.ACCEPTED,timeslot.getStartDate(),
                    timeslot.getEndDate(),accommodation.getName());
            int sum=0;
            for(Request reservation:reservations){
                if(reservation.getTimeSlot().getEndDate().isBefore(LocalDate.now())){
                    sum+= (int) reservation.getPrice();
                }
            }
            Report report=new Report(accommodation.getId(),accommodation.getName(),sum,reservations.size(),new double[]{});
            reports.add(report);
            //reportRepository.save(report);
        }
        return reports;
    }

    @Override
    public Report findAnnualByAccommodation(String accommodationName, int year) {
        Report report = new Report();
        double[] profitByMonth = new double[12];

        Collection<Request> acceptedRequests = requestService.findReservationsByYear(accommodationName,year);

        for (Request request : acceptedRequests) {
            if(request.getTimeSlot().getEndDate().isBefore(LocalDate.now())){
                LocalDate startDate = request.getTimeSlot().getStartDate();
                LocalDate endDate = request.getTimeSlot().getEndDate();

                int startMonth = startDate.getMonthValue()-1;
                int endMonth = endDate.getMonthValue()-1;

                double profit = request.getPrice();

                if (startMonth == endMonth) {
                    profitByMonth[startMonth] += profit;
                } else {
                    int startDays = startDate.lengthOfMonth() - startDate.getDayOfMonth();
                    int endDays = endDate.getDayOfMonth();
                    double profitPerNight = profit / (startDays + endDays);
                    profitByMonth[startMonth] += startDays * profitPerNight;
                    profitByMonth[endMonth] += endDays * profitPerNight;
                }
            }
        }
        report.setAccommodationName(accommodationName);
        report.setProfitByMonth(profitByMonth);
        //reportRepository.save(report);
        return report;
    }

}
