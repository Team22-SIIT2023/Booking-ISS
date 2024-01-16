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
    private int guestNumber;
    private AccommodationDTO accommodation;
    private RequestStatus status;

    public RequestDTO(Long id, TimeSlot timeSlot, double price, Guest guest, AccommodationDTO accommodationDTO, RequestStatus status,int number) {
        this.id = id;
        this.timeSlot = timeSlot;
        this.price = price;
        this.guest = guest;
        this.accommodation = accommodationDTO;
        this.status = status;
        this.guestNumber=number;
    }
    public RequestDTO(Long id, TimeSlot timeSlot, double price, AccommodationDTO accommodationDTO, RequestStatus status,int number) {
        this.id = id;
        this.timeSlot = timeSlot;
        this.price = price;
        this.accommodation = accommodationDTO;
        this.status = status;
        this.guestNumber=number;
    }

    public RequestDTO(Request request) {
        this(request.getId(), request.getTimeSlot(), request.getPrice(), request.getGuest(), null,
                request.getStatus(),request.getGuestNumber());
    }

    @Override
    public String toString() {
        return "RequestDTO{" +
                "id=" + id +
                ", timeSlot=" + timeSlot +
                ", price=" + price +
                ", guest=" + guest +
                ", accommodation=" + accommodation +
                ", status=" + status +
                '}';
    }
}
