package com.booking.BookingApp.service;

import com.booking.BookingApp.domain.Notification;
import com.booking.BookingApp.domain.enums.NotificationType;
import com.booking.BookingApp.dto.NotificationSettingsDTO;
import com.booking.BookingApp.repository.NotificationRepository;
import com.booking.BookingApp.service.interfaces.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class NotificationService implements INotificationService{

    @Autowired
    NotificationRepository notificationRepository;

    @Override
    public Notification findOne(Long id) {
//        return notificationRepository.findById(id);
        return new Notification(1L, "New message received",LocalDate.now(),true, NotificationType.HOST_RATED,false);
    }

    @Override
    public Collection<Notification> findAllForGuest(Long id) {return data();}

    @Override
    public Collection<Notification> findAllForHost(Long id) {
        return data();
    }

    @Override
    public Notification createHostNotification(Long id, Notification notification) throws Exception {
        return new Notification(1L, "New message received",LocalDate.now(),true, NotificationType.HOST_RATED,false);
    }

    @Override
    public Notification createGuestNotification(Long id, Notification notification) throws Exception {
        return new Notification(1L, "New message received",LocalDate.now(),true, NotificationType.HOST_RATED,false);
    }

    @Override
    public Notification update(Notification notification, Notification notificationForUpdate) throws Exception {
        return new Notification(1L, "New message received",LocalDate.now(),true, NotificationType.HOST_RATED,false);
    }
    @Override
    public void delete(Long id) {}

    @Override
    public Collection<Notification> updateGuestSettings(Long id, NotificationSettingsDTO settingsDTO) {
        return data();
    }

    @Override
    public Collection<Notification> updateHostSettings(Long id, NotificationSettingsDTO settingsDTO) {
        return data();
    }


    public Collection<Notification> data() {
        Collection<Notification> notificationList = new ArrayList<>();

        notificationList.add(new Notification(1L, "New message received", LocalDate.now(),true, NotificationType.HOST_RATED,false));
        notificationList.add(new Notification(2L, "Reminder: Meeting at 10 AM", LocalDate.now(),true, NotificationType.RESERVATION_REQUEST,false));
        notificationList.add(new Notification(3L, "System update available", LocalDate.now(),true, NotificationType.ACCOMMODATION_RATED,false));

        return notificationList;
    }
}
