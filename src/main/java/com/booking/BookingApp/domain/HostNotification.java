package com.booking.BookingApp.domain;

import com.booking.BookingApp.domain.enums.NotificationType;
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
@Table(name = "host_notifications")
public class HostNotification extends Notification{

    @Column(name = "host_id")
    private Long hostId;

    public HostNotification(Long id, String text, LocalDate date, boolean turnedOn, NotificationType type, Long hostId) {
        super(id, text, date, turnedOn, type);
        this.hostId = hostId;
    }
}
