package com.booking.BookingApp.dto;


import com.booking.BookingApp.domain.enums.NotificationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class NotificationDTO {
    private Long id;
    private String text;
    private LocalDate date;
    private NotificationType type;

    public NotificationDTO(Long id, String text, LocalDate date, NotificationType type) {
        this.id = id;
        this.text = text;
        this.date = date;
        this.type = type;
    }

    @Override
    public String toString() {
        return "NotificationDTO{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", date=" + date +
                ", type=" + type +
                '}';
    }
}
