package com.booking.BookingApp.service.interfaces;

import com.booking.BookingApp.domain.Accommodation;
import com.booking.BookingApp.domain.Notification;
import com.booking.BookingApp.domain.enums.NotificationType;
import com.booking.BookingApp.dto.NotificationDTO;
import com.booking.BookingApp.dto.NotificationSettingsDTO;

import java.util.Collection;

public interface INotificationService {
    Notification findOne(Long id);
    Collection<Notification> findAllForGuest(Long id);
    Collection<Notification> findAllForHost(Long id);
    Notification createHostNotification(Long id,Notification notification) throws Exception;

    Notification createGuestNotification(Long id,Notification notification) throws Exception;

    Notification update(Notification notification) throws Exception;

    void delete(Long id);

    Collection<Notification> updateGuestSettings(Long id, NotificationSettingsDTO settingsDTO);

    Collection<Notification> updateHostSettings(Long id, NotificationSettingsDTO settingsDTO);
}
