package com.booking.BookingApp.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PricelistItem {
    private TimeSlot timeSlot;
    private double price;

    public PricelistItem(TimeSlot timeSlot, double price) {
        this.timeSlot = timeSlot;
        this.price = price;
    }

    @Override
    public String toString() {
        return "PricelistItem{" +
                "timeSlot=" + timeSlot +
                ", price=" + price +
                '}';
    }
}
