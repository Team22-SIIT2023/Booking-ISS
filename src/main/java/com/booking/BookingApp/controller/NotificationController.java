package com.booking.BookingApp.controller;

import com.booking.BookingApp.domain.Accommodation;
import com.booking.BookingApp.domain.Notification;
import com.booking.BookingApp.dto.FavoriteAccommodationDTO;
import com.booking.BookingApp.dto.NotificationDTO;
import com.booking.BookingApp.mapper.FavoriteAccommodationDTOMapper;
import com.booking.BookingApp.mapper.NotificationDTOMapper;
import com.booking.BookingApp.service.interfaces.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    @Autowired
    INotificationService notificationService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NotificationDTO> getNotification(@PathVariable("id") Long id) {
        Notification notification = notificationService.findOne(id);

        if (notification == null) {
            return new ResponseEntity<NotificationDTO>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<NotificationDTO>(NotificationDTOMapper.fromNotificationtoDTO(notification), HttpStatus.OK);
    }

    @GetMapping(value = "/guest/{guestId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<NotificationDTO>> getGuestNotifications(@PathVariable("guestId") Long id) {
        Collection<Notification> notifications=notificationService.findAllForGuest(id);
        Collection<NotificationDTO> notificationDTOS = notifications.stream()
                .map(NotificationDTOMapper::fromNotificationtoDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(notificationDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/host/{hostId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<NotificationDTO>> getHostNotifications(@PathVariable("hostId") Long id) {
        Collection<Notification> notifications=notificationService.findAllForHost(id);
        Collection<NotificationDTO> notificationDTOS = notifications.stream()
                .map(NotificationDTOMapper::fromNotificationtoDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(notificationDTOS, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NotificationDTO> createNotification(@RequestBody NotificationDTO notification) throws Exception {
        Notification savedNotification = notificationService.create(NotificationDTOMapper.fromDTOtoNotification(notification));
        return new ResponseEntity<NotificationDTO>(NotificationDTOMapper.fromNotificationtoDTO(savedNotification), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NotificationDTO> updateNotification(@RequestBody NotificationDTO notification, @PathVariable Long id)
            throws Exception {
        Notification notificationForUpdate = notificationService.findOne(id);
        notificationForUpdate.setId(notification.getId());
        notificationForUpdate.setText(notification.getText());
        notificationForUpdate.setType(notification.getType());
        notificationForUpdate.setTurnedOn(true);
        Notification updatedNotification = notificationService.update(notificationForUpdate);

        if (updatedNotification == null) {
            return new ResponseEntity<NotificationDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<NotificationDTO>(NotificationDTOMapper.fromNotificationtoDTO(updatedNotification), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<NotificationDTO> deleteNotification(@PathVariable("id") Long id) {
        notificationService.delete(id);
        return new ResponseEntity<NotificationDTO>(HttpStatus.NO_CONTENT);
    }
}
