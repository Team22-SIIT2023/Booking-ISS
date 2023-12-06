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
@Table(name = "guest_notifications")
public class GuestNotification extends Notification{

    @Column(name = "guest_id")
    private Long guestId;

    public GuestNotification(Long id, String text, LocalDate date, boolean turnedOn, NotificationType type, Long guestId) {
        super(id, text, date, turnedOn, type);
        this.guestId = guestId;
    }
}
