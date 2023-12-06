package com.booking.BookingApp.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "pricelist_items")
public class PricelistItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = {CascadeType.ALL})
    private TimeSlot timeSlot;

    @Column(name = "price")
    private double price;

    public PricelistItem(Long id, TimeSlot timeSlot, double price) {
        this.id = id;
        this.timeSlot = timeSlot;
        this.price = price;
    }

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
