package com.booking.BookingApp.domain;

import com.booking.BookingApp.domain.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "accommodation_comments")
public class AccommodationComments extends Comments{

    @Column(name = "accommodation_id")
    private Long accommodationId;

    public AccommodationComments(Long id, String text, LocalDate date, double rating, Status status, Guest guest, Long accommodationId) {
        super(id, text, date, rating, status, guest);
        this.accommodationId = accommodationId;
    }
}
