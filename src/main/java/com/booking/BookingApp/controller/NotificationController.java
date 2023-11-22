package com.booking.BookingApp.controller;

import com.booking.BookingApp.domain.Accommodation;
import com.booking.BookingApp.domain.Notification;
import com.booking.BookingApp.dto.NotificationDTO;
import com.booking.BookingApp.service.interfaces.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    @Autowired
    INotificationService notificationService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NotificationDTO> getNotification(@PathVariable("id") Long id) {
        NotificationDTO notification = notificationService.findOne(id);

        if (notification == null) {
            return new ResponseEntity<NotificationDTO>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<NotificationDTO>(notification, HttpStatus.OK);
    }

    @GetMapping(value = "/guest",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<NotificationDTO>> getGuestNotifications(@RequestParam("guestId") Long id) {
        Collection<NotificationDTO> notifications=notificationService.findAllForGuest (id);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @GetMapping(value="/host",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<NotificationDTO>> getHostNotifications(@RequestParam("hostId") Long id) {
        Collection<NotificationDTO> notifications=notificationService.findAllForHost(id);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NotificationDTO> createNotification(@RequestBody NotificationDTO notification) throws Exception {
        NotificationDTO savedNotification = notificationService.create(notification);
        return new ResponseEntity<NotificationDTO>(savedNotification, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NotificationDTO> updateNotification(@RequestBody NotificationDTO notification, @PathVariable Long id)
            throws Exception {
        NotificationDTO notificationForUpdate = notificationService.findOne(id);
        //notificationForUpdate.copyValues(notification);

        NotificationDTO updatedNotification = notificationService.update(notificationForUpdate);

        if (updatedNotification == null) {
            return new ResponseEntity<NotificationDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<NotificationDTO>(updatedNotification, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<NotificationDTO> deleteNotification(@PathVariable("id") Long id) {
        notificationService.delete(id);
        return new ResponseEntity<NotificationDTO>(HttpStatus.NO_CONTENT);
    }
}
