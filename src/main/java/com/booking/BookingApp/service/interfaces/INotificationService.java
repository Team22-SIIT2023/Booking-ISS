package com.booking.BookingApp.service.interfaces;

import com.booking.BookingApp.domain.GuestNotificationSettings;
import com.booking.BookingApp.domain.HostNotificationSettings;
import com.booking.BookingApp.domain.Notification;
import com.booking.BookingApp.domain.enums.NotificationType;
import com.booking.BookingApp.dto.NotificationSettingsDTO;

import java.util.Collection;

public interface INotificationService {
    Notification findOne(Long id);
    Collection<Notification> findAllForGuest(Long id);
    Collection<Notification> findAllForHost(Long id);
    Notification createHostNotification(Long id,Notification notification) throws Exception;

    Notification createGuestNotification(Long id,Notification notification) throws Exception;

    Notification update(Notification notification, Notification notificationForUpdate) throws Exception;

    void delete(Long id);

    Notification createUserNotification(Notification notification) throws Exception;

    Collection<Notification> findAllForUser(Long id);

    boolean updateGuestSettings(Long id, GuestNotificationSettings settings);

    boolean updateHostSettings(Long id, HostNotificationSettings settings);

//    Collection<Notification> updateSettings(Long id, NotificationType type);
}
