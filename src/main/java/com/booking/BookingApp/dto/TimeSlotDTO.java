package com.booking.BookingApp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class TimeSlotDTO {
    private LocalDate startDate;
    private LocalDate endDate;

    public TimeSlotDTO(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "TimeSlotDTO{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
