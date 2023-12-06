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
@Table(name = "host_comments")
public class HostComments extends Comments{

    @Column(name = "host_id")
    private Long hostId;

    public HostComments(Long id, String text, LocalDate date, double rating, Status status, Guest guest, Long hostId) {
        super(id, text, date, rating, status, guest);
        this.hostId = hostId;
    }
}
