package com.booking.BookingApp.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GuestNotification extends Notification{
    private Long guestId;
}
