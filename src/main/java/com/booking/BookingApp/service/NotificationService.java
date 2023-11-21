package com.booking.BookingApp.service;

import com.booking.BookingApp.domain.Notification;
import com.booking.BookingApp.service.interfaces.INotificationService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class NotificationService implements INotificationService{
    @Override
    public Notification create(Notification notification) throws Exception {
        return null;
    }

    @Override
    public Notification update(Notification notification) throws Exception {
        return null;
    }

    @Override
    public void delete(Long id) {}

    @Override
    public Notification findOne(Long id) {
        return null;
    }

    @Override
    public Collection<Notification> findAllForGuest(Long id) {
        return null;
    }

    @Override
    public Collection<Notification> findAllForHost(Long id) {
        return null;
    }
}
