package com.booking.BookingApp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReportDTO {

    private Long accommodationId;
    private int totalProfit;
    private int numberOfReservations;

    public ReportDTO(Long accommodationId,int total, int numOfRes) {
        this.accommodationId = accommodationId;
        this.totalProfit = total;
        this.numberOfReservations = numOfRes;
    }

    @Override
    public String toString() {
        return "ReportDTO{" +
                "AccommodationId=" + accommodationId +
                "TotalProfit=" + totalProfit +
                ", numberOfReservations=" + numberOfReservations +
                '}';
    }
}
