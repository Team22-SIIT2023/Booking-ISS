package com.booking.BookingApp.service;

import com.booking.BookingApp.domain.*;
import com.booking.BookingApp.domain.enums.NotificationType;
import com.booking.BookingApp.dto.NotificationSettingsDTO;
import com.booking.BookingApp.repository.GuestNotificationSettingsRepository;
import com.booking.BookingApp.repository.HostNotificationSettingsRepository;
import com.booking.BookingApp.repository.NotificationRepository;
import com.booking.BookingApp.repository.NotificationSettingsRepository;
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

    @Autowired
    UserService userService;

    @Autowired
    HostNotificationSettingsRepository hostNotificationSettingsRepository;

    @Autowired
    GuestNotificationSettingsRepository guestNotificationSettingsRepository;

    @Override
    public Collection<Notification> findAllForUser(Long userId) {
        return notificationRepository.findAllByUser_Id(userId);

//        User user = userService.findOne(userId);
//        Collection<Notification> displayedNotifications = new ArrayList<>();
//
//        if (user.getAccount().getRoles().get(0).getName().equals("ROLE_GUEST")) {
//            GuestNotificationSettings guestSettings = guestNotificationSettingsRepository.findByUser_Id(userId);
//
//            for (Notification notification : notifications) {
//                if (notification.getType().equals(NotificationType.RESERVATION_RESPONSE)) {
//                    if (guestSettings.isRequestResponded()) {
//                        displayedNotifications.add(notification);
//                    }
//                }
//            }
//        }
//        else {
//            HostNotificationSettings hostSettings = hostNotificationSettingsRepository.findByUser_Id(userId);
//
//            for (Notification notification : notifications) {
//                if (notification.getType().equals(NotificationType.HOST_RATED)) {
//                    if (hostSettings.isRated()) {
//                        displayedNotifications.add(notification);
//                    }
//                }
//                if (notification.getType().equals(NotificationType.ACCOMMODATION_RATED)) {
//                    if (hostSettings.isAccommodationRated()) {
//                        displayedNotifications.add(notification);
//                    }
//                }
//                if (notification.getType().equals(NotificationType.RESERVATION_REQUEST)) {
//                    if (hostSettings.isReservationCancelled()) {
//                        displayedNotifications.add(notification);
//                    }
//                }if (notification.getType().equals(NotificationType.RESERVATION_CANCELLED)) {
//                    if (hostSettings.isReservationCancelled()) {
//                        displayedNotifications.add(notification);
//                    }
//                }
//            }
//        }
    }

    @Override
    public Notification findOne(Long id) {
//        return notificationRepository.findById(id);
        return new Notification(1L, "New message received",LocalDate.now(), NotificationType.HOST_RATED,false);
    }

    @Override
    public Collection<Notification> findAllForGuest(Long id) {return data();}

    @Override
    public Collection<Notification> findAllForHost(Long id) {
        return data();
    }

    @Override
    public Notification createHostNotification(Long id, Notification notification) throws Exception {
        return new Notification(1L, "New message received",LocalDate.now(), NotificationType.HOST_RATED,false);
    }

    @Override
    public Notification createUserNotification(Notification notification) throws Exception {
        return notificationRepository.save(notification);
    }


    @Override
    public Notification createGuestNotification(Long id, Notification notification) throws Exception {
        return new Notification(1L, "New message received",LocalDate.now(), NotificationType.HOST_RATED,false);
    }

    @Override
    public Notification update(Notification notification, Notification notificationForUpdate) throws Exception {
        return new Notification(1L, "New message received",LocalDate.now(), NotificationType.HOST_RATED,false);
    }
    @Override
    public void delete(Long id) {}

    @Override
    public boolean updateGuestSettings(Long id, GuestNotificationSettings settings) {
        GuestNotificationSettings notificationSettings = guestNotificationSettingsRepository.findByUser_Id(id);
        if (notificationSettings == null) {
            return false;
        }
//        notificationSettings.setRequestResponded(!notificationSettings.isRequestResponded());
        notificationSettings.setRequestResponded(settings.isRequestResponded());
        guestNotificationSettingsRepository.save(notificationSettings);
        return true;
    }

    @Override
    public boolean updateHostSettings(Long id, HostNotificationSettings settings) {
        HostNotificationSettings notificationSettings = hostNotificationSettingsRepository.findByUser_Id(id);

        if (notificationSettings == null) {
            return false;
        }

        notificationSettings.setRated(settings.isRated());
        notificationSettings.setReservationCancelled(settings.isReservationCancelled());
        notificationSettings.setRequestCreated(settings.isRequestCreated());
        notificationSettings.setAccommodationRated(settings.isAccommodationRated());
        hostNotificationSettingsRepository.save(notificationSettings);
        return true;
    }

//    @Override
//    public Collection<Notification> updateSettings(Long id, NotificationType type) {
//        return null;
//    }


    public Collection<Notification> data() {
        Collection<Notification> notificationList = new ArrayList<>();

        notificationList.add(new Notification(1L, "New message received", LocalDate.now(), NotificationType.HOST_RATED,false));
        notificationList.add(new Notification(2L, "Reminder: Meeting at 10 AM", LocalDate.now(), NotificationType.RESERVATION_REQUEST,false));
        notificationList.add(new Notification(3L, "System update available", LocalDate.now(), NotificationType.ACCOMMODATION_RATED,false));

        return notificationList;
    }
}
