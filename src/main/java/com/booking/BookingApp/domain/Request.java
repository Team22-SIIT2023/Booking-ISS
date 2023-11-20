package com.booking.BookingApp.domain;


import com.booking.BookingApp.domain.enums.RequestStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Request {
    private Long id;
    private TimeSlot timeSlot;
    private double price;
    private Guest guest;
    private Accommodation accommodation;
    private int guestNumber;
    private RequestStatus status;

    public Request(Long id, TimeSlot timeSlot, double price, Guest guest, Accommodation accommodation, int guestNumber, RequestStatus status) {
        this.id = id;
        this.timeSlot = timeSlot;
        this.price = price;
        this.guest = guest;
        this.accommodation = accommodation;
        this.guestNumber = guestNumber;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", timeSlot=" + timeSlot +
                ", price=" + price +
                ", guest=" + guest +
                ", accommodation=" + accommodation +
                ", guestNumber=" + guestNumber +
                ", status=" + status +
                '}';
    }
}

