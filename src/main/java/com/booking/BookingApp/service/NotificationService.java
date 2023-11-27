package com.booking.BookingApp.service;

import com.booking.BookingApp.domain.Notification;
import com.booking.BookingApp.domain.enums.NotificationType;
import com.booking.BookingApp.dto.NotificationDTO;
import com.booking.BookingApp.service.interfaces.INotificationService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class NotificationService implements INotificationService{
    @Override
    public Notification create(Notification notification) throws Exception {
        return new Notification(1L, "New message received",LocalDate.now(),true, NotificationType.HOST_RATED);
    }

    @Override
    public Notification update(Notification notification) throws Exception {
        return new Notification(1L, "New message received",LocalDate.now(),true, NotificationType.HOST_RATED);
    }

    @Override
    public void delete(Long id) {}

    @Override
    public Notification findOne(Long id) {

        return new Notification(1L, "New message received",LocalDate.now(),true, NotificationType.HOST_RATED);
    }

    @Override
    public Collection<Notification> findAllForGuest(Long id) {
        return data();
    }

    @Override
    public Collection<Notification> findAllForHost(Long id) {
        return data();
    }

    public Collection<Notification> data() {
        Collection<Notification> notificationList = new ArrayList<>();

        notificationList.add(new Notification(1L, "New message received", LocalDate.now(),true, NotificationType.HOST_RATED));
        notificationList.add(new Notification(2L, "Reminder: Meeting at 10 AM", LocalDate.now(),true, NotificationType.RESERVATION_REQUEST));
        notificationList.add(new Notification(3L, "System update available", LocalDate.now(),true, NotificationType.ACCOMMODATION_RATED));

        return notificationList;
    }
}
