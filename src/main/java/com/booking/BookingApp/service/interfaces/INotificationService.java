package com.booking.BookingApp.service.interfaces;

import com.booking.BookingApp.domain.Accommodation;
import com.booking.BookingApp.domain.Notification;

import java.util.Collection;

public interface INotificationService {
    Notification findOne(Long id);
    Collection<Notification> findAllForGuest(Long id);
    Collection<Notification> findAllForHost(Long id);
    Notification create(Notification notification) throws Exception;

    Notification update(Notification notification) throws Exception;

    void delete(Long id);


}
