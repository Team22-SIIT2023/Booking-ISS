package com.booking.BookingApp.domain;


import com.booking.BookingApp.domain.enums.RequestStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = {CascadeType.ALL})
    private TimeSlot timeSlot;

    @Column(name = "price")
    private double price;

    @ManyToOne(cascade = {CascadeType.ALL})
    private Guest guest;

    @ManyToOne(cascade = {CascadeType.ALL})
    private Accommodation accommodation;

    @Column(name = "guest_number")
    private int guestNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "request_status")
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

