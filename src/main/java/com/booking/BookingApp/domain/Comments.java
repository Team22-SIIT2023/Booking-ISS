package com.booking.BookingApp.domain;

import com.booking.BookingApp.domain.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "comments")
//@MappedSuperclass
public class Comments {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "comment_text")
    private String text;

    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    private LocalDate date;

    @Column(name = "rating")
    private double rating;

    @Enumerated(EnumType.STRING)
    @Column(name = "commentStatus")
    private Status status;

    @ManyToOne(cascade = {CascadeType.ALL})
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
