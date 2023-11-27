package com.booking.BookingApp.dto;

import com.booking.BookingApp.domain.Comments;
import com.booking.BookingApp.domain.Guest;
import com.booking.BookingApp.domain.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class CommentsDTO {
    private Long id;
    private String text;
    private LocalDate date;
    private double rating;
    private Status status;
    private Guest guest;

    public CommentsDTO(Long id, String text, LocalDate date, double rating, Status status, Guest guest) {
        this.id = id;
        this.text = text;
        this.date = date;
        this.rating = rating;
        this.status = status;
        this.guest = guest;
    }

    public CommentsDTO(Comments comments) {
        this(comments.getId(), comments.getText(), comments.getDate(), comments.getRating(), comments.getStatus(),
                comments.getGuest());
    }

    @Override
    public String toString() {
        return "CommentsDTO{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", date=" + date +
                ", rating=" + rating +
                ", status=" + status +
                ", guest=" + guest +
                '}';
    }
}
