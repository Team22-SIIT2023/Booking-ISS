package com.booking.BookingApp.service.interfaces;

import com.booking.BookingApp.domain.GuestNotificationSettings;
import com.booking.BookingApp.domain.HostNotificationSettings;
import com.booking.BookingApp.domain.Notification;
import com.booking.BookingApp.domain.enums.NotificationType;
import com.booking.BookingApp.dto.NotificationSettingsDTO;

import java.util.Collection;

public interface INotificationService {
    void delete(Long id);

    Notification createUserNotification(Notification notification) throws Exception;

    Collection<Notification> findAllForUser(Long id);

    GuestNotificationSettings updateGuestSettings(Long id, GuestNotificationSettings settings);

    HostNotificationSettings updateHostSettings(Long id, HostNotificationSettings settings);

//    Collection<Notification> updateSettings(Long id, NotificationType type);
}
