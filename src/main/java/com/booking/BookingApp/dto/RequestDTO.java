package com.booking.BookingApp.dto;

import com.booking.BookingApp.domain.Guest;
import com.booking.BookingApp.domain.Request;
import com.booking.BookingApp.domain.TimeSlot;
import com.booking.BookingApp.domain.enums.RequestStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RequestDTO {
    private Long id;
    private TimeSlot timeSlot;
    private double price;
    private Guest guest;
    private AccommodationDTO accommodationDTO;
    private RequestStatus status;

    public RequestDTO(Long id, TimeSlot timeSlot, double price, Guest guest, AccommodationDTO accommodationDTO, RequestStatus status) {
        this.id = id;
        this.timeSlot = timeSlot;
        this.price = price;
        this.guest = guest;
        this.accommodationDTO = accommodationDTO;
        this.status = status;
    }

    public RequestDTO(Request request) {
        this(request.getId(), request.getTimeSlot(), request.getPrice(), request.getGuest(), null,
                request.getStatus());
    }

    @Override
    public String toString() {
        return "RequestDTO{" +
                "id=" + id +
                ", timeSlot=" + timeSlot +
                ", price=" + price +
                ", guest=" + guest +
                ", accommodation=" + accommodationDTO +
                ", status=" + status +
                '}';
    }
}
