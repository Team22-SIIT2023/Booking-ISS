package com.booking.BookingApp.domain;

import com.booking.BookingApp.domain.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class Comments {
    private Long id;
    private String text;
    private LocalDate date;
    private double rating;
    private Status status;
    private Guest guest;

    public Comments(Long id, String text, LocalDate date, double rating, Status status, Guest guest) {
        this.id = id;
        this.text = text;
        this.date = date;
        this.rating = rating;
        this.status = status;
        this.guest = guest;
    }
    @Override
    public String toString() {
        return "Comments{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", date=" + date +
                ", rating=" + rating +
                ", status=" + status +
                ", guest=" + guest +
                '}';
    }
}
