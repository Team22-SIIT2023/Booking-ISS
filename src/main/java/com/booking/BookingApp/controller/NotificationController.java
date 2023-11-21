package com.booking.BookingApp.controller;

import com.booking.BookingApp.domain.Accommodation;
import com.booking.BookingApp.domain.Notification;
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
    public ResponseEntity<Notification> getNotification(@PathVariable("id") Long id) {
        Notification notification = notificationService.findOne(id);

        if (notification == null) {
            return new ResponseEntity<Notification>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Notification>(notification, HttpStatus.OK);
    }

    @GetMapping(value = "/guest",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Notification>> getGuestNotifications(@RequestParam("guestId") Long id) {
        Collection<Notification> notifications=notificationService.findAllForGuest (id);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @GetMapping(value="/host",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Notification>> getHostNotifications(@RequestParam("hostId") Long id) {
        Collection<Notification> notifications=notificationService.findAllForHost(id);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Notification> createNotification(@RequestBody Notification notification) throws Exception {
        Notification savedNotification = notificationService.create(notification);
        return new ResponseEntity<Notification>(savedNotification, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Notification> updateNotification(@RequestBody Notification notification, @PathVariable Long id)
            throws Exception {
        Notification notificationForUpdate = notificationService.findOne(id);
        //accommodationForUpdate.copyValues(accommodation);

        Notification updatedNotification = notificationService.update(notificationForUpdate);

        if (updatedNotification == null) {
            return new ResponseEntity<Notification>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<Notification>(updatedNotification, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Notification> deleteNotification(@PathVariable("id") Long id) {
        notificationService.delete(id);
        return new ResponseEntity<Notification>(HttpStatus.NO_CONTENT);
    }
}
