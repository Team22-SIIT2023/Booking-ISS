package com.booking.BookingApp.domain;

import com.booking.BookingApp.domain.enums.RequestStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToOne(cascade = {CascadeType.ALL},orphanRemoval = true)
    private TimeSlot timeSlot;

    @Column(name = "price")
    private double price;


    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Guest guest;
  
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(cascade = {CascadeType.REMOVE})
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

