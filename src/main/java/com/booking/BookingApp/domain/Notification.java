package com.booking.BookingApp.domain;

import com.booking.BookingApp.domain.enums.NotificationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class Notification {
    private Long id;
    private String text;
    private LocalDate date;
    private boolean turnedOn;
    private NotificationType type;

    public Notification(Long id, String text, LocalDate date, boolean turnedOn, NotificationType type) {
        this.id = id;
        this.text = text;
        this.date = date;
        this.turnedOn = turnedOn;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", date=" + date +
                ", turnedOn=" + turnedOn +
                ", type=" + type +
                '}';
    }
}
