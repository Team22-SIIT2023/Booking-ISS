package com.booking.BookingApp.controller;

import com.booking.BookingApp.domain.Request;
import com.booking.BookingApp.domain.enums.RequestStatus;
import com.booking.BookingApp.dto.RequestDTO;
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
    public ResponseEntity<Collection<RequestDTO>> getRequests() {
        Collection<RequestDTO> requests = requestService.findAll();
        return new ResponseEntity<Collection<RequestDTO>>(requests, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RequestDTO> getById(@PathVariable("id") Long id) {
        RequestDTO request = requestService.findById(id);
        return new ResponseEntity<RequestDTO>(request, HttpStatus.OK);
    }

    @GetMapping(value = "/host", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<RequestDTO>> getByHostId(@RequestParam("hostId") Long id) {
        Collection<RequestDTO> hostRequests = requestService.findByHostId(id);
        return new ResponseEntity<Collection<RequestDTO>>(hostRequests, HttpStatus.OK);
    }

    @GetMapping(value = "/guest", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<RequestDTO>> getByGuestId(@RequestParam("guestId") Long id) {
        Collection<RequestDTO> guestRequests = requestService.findByGuestId(id);
        return new ResponseEntity<Collection<RequestDTO>>(guestRequests, HttpStatus.OK);
    }

    @GetMapping(value = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<RequestDTO>> getByStatus(@RequestParam("status") RequestStatus status) {
        Collection<RequestDTO> guestRequests = requestService.findByStatus(status);
        return new ResponseEntity<Collection<RequestDTO>>(guestRequests, HttpStatus.OK);
    }

    @GetMapping(value = "/reservations", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<RequestDTO>> getGuestReservation(@RequestParam("guestId") Long id,
                                                                   @RequestParam("status") RequestStatus status) {
        Collection<RequestDTO> guestRequests = requestService.findReservationByGuestId(id, status);
        return new ResponseEntity<Collection<RequestDTO>>(guestRequests, HttpStatus.OK);
    }

    @GetMapping(value = "/hostRequest", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<RequestDTO>> getWaitingRequest(@RequestParam("hostId") Long id) {
        Collection<RequestDTO> guestRequests = requestService.findWaitingRequest(id);
        return new ResponseEntity<Collection<RequestDTO>>(guestRequests, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RequestDTO> createRequest(@RequestBody RequestDTO request) {
        RequestDTO savedRequest = requestService.create(request);
        return new ResponseEntity<RequestDTO>(savedRequest, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RequestDTO> updateRequest(@PathVariable("id") Long id) {
        RequestDTO requestForUpdated = requestService.findById(id);
        // zavrsiti !!!!!
        return new ResponseEntity<RequestDTO>(requestForUpdated, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<RequestDTO> deleteRequest(@PathVariable("id") Long id) {
        requestService.delete(id);
        return new ResponseEntity<RequestDTO>(HttpStatus.NO_CONTENT);
    }
}