package com.booking.BookingApp.dto;


import com.booking.BookingApp.domain.TimeSlot;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PricelistItemDTO {
    private TimeSlotDTO timeSlotDTO;
    private double price;

    public PricelistItemDTO(TimeSlotDTO timeSlotDTO, double price) {
        this.timeSlotDTO = timeSlotDTO;
        this.price = price;
    }

    @Override
    public String toString() {
        return "PricelistItemDTO{" +
                "timeSlotDTO=" + timeSlotDTO +
                ", price=" + price +
                '}';
    }
}
