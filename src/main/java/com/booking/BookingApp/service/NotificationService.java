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
    public NotificationDTO create(NotificationDTO notification) throws Exception {
        return null;
    }

    @Override
    public NotificationDTO update(NotificationDTO notification) throws Exception {
        return null;
    }

    @Override
    public void delete(Long id) {}

    @Override
    public NotificationDTO findOne(Long id) {

        return new NotificationDTO(1L, "New message received", LocalDate.now(), NotificationType.HOST_RATED);
    }

    @Override
    public Collection<NotificationDTO> findAllForGuest(Long id) {
        return data();
    }

    @Override
    public Collection<NotificationDTO> findAllForHost(Long id) {
        return data();
    }

    public Collection<NotificationDTO> data() {
        Collection<NotificationDTO> notificationList = new ArrayList<>();

        notificationList.add(new NotificationDTO(1L, "New message received", LocalDate.now(), NotificationType.HOST_RATED));
        notificationList.add(new NotificationDTO(2L, "Reminder: Meeting at 10 AM", LocalDate.now(), NotificationType.RESERVATION_REQUEST));
        notificationList.add(new NotificationDTO(3L, "System update available", LocalDate.now(), NotificationType.ACCOMMODATION_RATED));

        return notificationList;
    }
}
