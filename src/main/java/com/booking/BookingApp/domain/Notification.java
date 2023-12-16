package com.booking.BookingApp.domain;

import com.booking.BookingApp.domain.enums.NotificationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "notifications")
@SQLDelete(sql = "UPDATE notifications SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
//@MappedSuperclass
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "notification_text")
    private String text;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "turnedON")
    private boolean turnedOn;

    @Column(name = "notification_type")
    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @Column(name="deleted")
    private boolean deleted = Boolean.FALSE;

    public Notification(Long id, String text, LocalDate date, boolean turnedOn, NotificationType type, boolean deleted) {
        this.id = id;
        this.text = text;
        this.date = date;
        this.turnedOn = turnedOn;
        this.type = type;
        this.deleted=deleted;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", date=" + date +
                ", turnedOn=" + turnedOn +
                ", type=" + type +
                ",deleted=" + deleted +
                '}';
    }
}
