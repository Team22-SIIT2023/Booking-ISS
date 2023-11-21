package com.booking.BookingApp.controller;

import com.booking.BookingApp.domain.Request;
import com.booking.BookingApp.domain.enums.RequestStatus;
import com.booking.BookingApp.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/requests")
public class RequestController {

    @Autowired
    private RequestService requestService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Request>> getRequests() {
        Collection<Request> requests = requestService.findAll();
        return new ResponseEntity<Collection<Request>>(requests, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Request> getById(@PathVariable("id") Long id) {
        Request request = requestService.findById(id);
        return new ResponseEntity<Request>(request, HttpStatus.OK);
    }

    @GetMapping(value = "/host", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Request>> getByHostId(@RequestParam("hostId") Long id) {
        Collection<Request> hostRequests = requestService.findByHostId(id);
        return new ResponseEntity<Collection<Request>>(hostRequests, HttpStatus.OK);
    }

    @GetMapping(value = "/guest", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Request>> getByGuestId(@RequestParam("guestId") Long id) {
        Collection<Request> guestRequests = requestService.findByGuestId(id);
        return new ResponseEntity<Collection<Request>>(guestRequests, HttpStatus.OK);
    }

    @GetMapping(value = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Request>> getByStatus(@RequestParam("status") RequestStatus status) {
        Collection<Request> guestRequests = requestService.findByStatus(status);
        return new ResponseEntity<Collection<Request>>(guestRequests, HttpStatus.OK);
    }

    @GetMapping(value = "/reservations", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Request>> getGuestReservation(@RequestParam("guestId") Long id,
                                                                   @RequestParam("status") RequestStatus status) {
        Collection<Request> guestRequests = requestService.findReservationByGuestId(id, status);
        return new ResponseEntity<Collection<Request>>(guestRequests, HttpStatus.OK);
    }

    @GetMapping(value = "/waitingStatus", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Request>> getWaitingRequest(@RequestParam("waitingStatus") RequestStatus status) {
        Collection<Request> guestRequests = requestService.findWaitingRequest(status);
        return new ResponseEntity<Collection<Request>>(guestRequests, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Request> createRequest(@RequestBody Request request) {
        Request savedRequest = requestService.create(request);
        return new ResponseEntity<Request>(savedRequest, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Request> updateRequest(@PathVariable("id") Long id) {
        Request requestForUpdated = requestService.findById(id);
        // zavrsiti !!!!!
        return new ResponseEntity<Request>(requestForUpdated, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Request> deleteRequest(@PathVariable("id") Long id) {
        requestService.delete(id);
        return new ResponseEntity<Request>(HttpStatus.NO_CONTENT);
    }
}