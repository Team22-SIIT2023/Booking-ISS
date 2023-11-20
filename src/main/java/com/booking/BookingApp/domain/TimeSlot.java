package com.booking.BookingApp.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class TimeSlot {
    private LocalDate startDate;
    private LocalDate endDate;

    public TimeSlot(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "TimeSlot{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
