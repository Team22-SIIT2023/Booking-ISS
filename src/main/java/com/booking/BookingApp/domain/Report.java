package com.booking.BookingApp.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Report {

    private Long accommodationId;
    private int totalProfit;
    private int numberOfReservations;

    public Report(Long accommodationId,int total, int numOfRes) {
        this.accommodationId = accommodationId;
        this.totalProfit = total;
        this.numberOfReservations = numOfRes;
    }

    @Override
    public String toString() {
        return "Report{" +
                "AccommodationId=" + accommodationId +
                "TotalProfit=" + totalProfit +
                ", numberOfReservations=" + numberOfReservations +
                '}';
    }
}