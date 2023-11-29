package com.booking.BookingApp.controller;

import com.booking.BookingApp.domain.Notification;
import com.booking.BookingApp.domain.enums.AccommodationType;
import com.booking.BookingApp.domain.enums.NotificationType;
import com.booking.BookingApp.dto.NotificationDTO;
import com.booking.BookingApp.dto.NotificationSettingsDTO;
import com.booking.BookingApp.mapper.NotificationDTOMapper;
import com.booking.BookingApp.service.interfaces.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.Collectors;

@CrossOrigin
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

    @PostMapping(value = "/guest/{guestId}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NotificationDTO> createGuestNotification(@RequestBody NotificationDTO notification,
                                                                   @PathVariable("guestId") Long id) throws Exception {
        Notification savedNotification = notificationService.createGuestNotification(id,NotificationDTOMapper.fromDTOtoNotification(notification));
        return new ResponseEntity<NotificationDTO>(NotificationDTOMapper.fromNotificationtoDTO(savedNotification), HttpStatus.CREATED);
    }
    @PostMapping(value = "/host/{hostId}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NotificationDTO> createHostNotification(@RequestBody NotificationDTO notification,
                                                                   @PathVariable("hostId") Long id) throws Exception {
        Notification savedNotification = notificationService.createHostNotification(id,NotificationDTOMapper.fromDTOtoNotification(notification));
        return new ResponseEntity<NotificationDTO>(NotificationDTOMapper.fromNotificationtoDTO(savedNotification), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NotificationDTO> updateNotification(@RequestBody NotificationDTO notificationDTO, @PathVariable("id") Long id)
            throws Exception {
        Notification notificationForUpdate = notificationService.findOne(id);
        Notification notification=NotificationDTOMapper.fromDTOtoNotification(notificationDTO);
        Notification updatedNotification = notificationService.update(notification,notificationForUpdate);

        if (updatedNotification == null) {
            return new ResponseEntity<NotificationDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<NotificationDTO>(NotificationDTOMapper.fromNotificationtoDTO(updatedNotification), HttpStatus.OK);
    }
    @PutMapping(value = "/guest/{guestId}/settings", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<NotificationDTO>> updateGuestNotificationSettings(@PathVariable("guestId") Long id,
                                                                               @RequestBody NotificationSettingsDTO settingsDTO) throws Exception {

        Collection<Notification> notifications= notificationService.updateGuestSettings(id,settingsDTO);
        Collection<NotificationDTO> notificationDTOS = notifications.stream()
                .map(NotificationDTOMapper::fromNotificationtoDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(notificationDTOS, HttpStatus.OK);
    }
    @PutMapping(value = "/host/{hostId}/settings", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<NotificationDTO>> updateHostNotificationSettings(@PathVariable("hostId") Long id,
                                                                              @RequestBody NotificationSettingsDTO settingsDTO) throws Exception {

        Collection<Notification> notifications= notificationService.updateHostSettings(id,settingsDTO);
        Collection<NotificationDTO> notificationDTOS = notifications.stream()
                .map(NotificationDTOMapper::fromNotificationtoDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(notificationDTOS, HttpStatus.OK);
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<NotificationDTO> deleteNotification(@PathVariable("id") Long id) {
        notificationService.delete(id);
        return new ResponseEntity<NotificationDTO>(HttpStatus.NO_CONTENT);
    }
}
