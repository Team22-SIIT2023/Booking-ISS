package com.booking.BookingApp.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Report {

    private int totalProfit;
    private int numberOfReservations;

    public Report(int total, int numOfRes) {
        this.totalProfit = total;
        this.numberOfReservations = numOfRes;
    }

    @Override
    public String toString() {
        return "Report{" +
                "TotalProfit=" + totalProfit +
                ", numberOfReservations=" + numberOfReservations +
                '}';
    }
}