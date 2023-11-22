package com.booking.BookingApp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReportDTO {

    private int totalProfit;
    private int numberOfReservations;

    public ReportDTO(int total, int numOfRes) {
        this.totalProfit = total;
        this.numberOfReservations = numOfRes;
    }

    @Override
    public String toString() {
        return "ReportDTO{" +
                "TotalProfit=" + totalProfit +
                ", numberOfReservations=" + numberOfReservations +
                '}';
    }
}
