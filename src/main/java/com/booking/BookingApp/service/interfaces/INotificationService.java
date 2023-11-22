package com.booking.BookingApp.service.interfaces;

import com.booking.BookingApp.domain.Accommodation;
import com.booking.BookingApp.domain.Notification;
import com.booking.BookingApp.dto.NotificationDTO;

import java.util.Collection;

public interface INotificationService {
    NotificationDTO findOne(Long id);
    Collection<NotificationDTO> findAllForGuest(Long id);
    Collection<NotificationDTO> findAllForHost(Long id);
    NotificationDTO create(NotificationDTO notification) throws Exception;

    NotificationDTO update(NotificationDTO notification) throws Exception;

    void delete(Long id);
}
