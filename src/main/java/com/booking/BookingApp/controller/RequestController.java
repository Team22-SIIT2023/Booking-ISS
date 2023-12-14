package com.booking.BookingApp.controller;

import com.booking.BookingApp.domain.Request;
import com.booking.BookingApp.domain.enums.RequestStatus;
import com.booking.BookingApp.dto.RequestDTO;
import com.booking.BookingApp.mapper.RequestDTOMapper;
import com.booking.BookingApp.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/requests")
public class RequestController {

    @Autowired
    private RequestService requestService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_GUEST') or hasAuthority('ROLE_HOST')")
    public ResponseEntity<Collection<RequestDTO>> getRequests(@RequestParam(value = "status", required = false) RequestStatus status,
                                                              @RequestParam(value = "begin", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate begin,
                                                              @RequestParam(value = "end", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate end,
                                                              @RequestParam(value = "accommodationName", required = false) String accommodationName) {
        Collection<Request> requests = requestService.findAll(status, begin, end, accommodationName);

        Collection<RequestDTO> requestsDTO = requests.stream()
                .map(RequestDTOMapper::fromRequesttoDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<Collection<RequestDTO>>(requestsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_GUEST') or hasAuthority('ROLE_HOST')")
    public ResponseEntity<RequestDTO> getById(@PathVariable("id") Long id) {
        Request request = requestService.findById(id);
        return new ResponseEntity<RequestDTO>(new RequestDTO(request), HttpStatus.OK);
    }

    //ako se ne prosledi status vracaju se svi zahtevi a ako se prosledi onda samo zahtevi koji su prosledjenog statusa
    @GetMapping(value = "/host/{hostId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('HOST')")
    public ResponseEntity<Collection<RequestDTO>> getByHostId(@PathVariable("hostId") Long id,
                                                              @RequestParam(value = "status", required = false) RequestStatus status) {
        Collection<Request> hostRequests = requestService.findByHostId(id, status);

        Collection<RequestDTO> hostRequestDTO = hostRequests.stream()
                .map(RequestDTOMapper::fromRequesttoDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<Collection<RequestDTO>>(hostRequestDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/guest/{guestId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('GUEST')")
    public ResponseEntity<Collection<RequestDTO>> getByGuestId(@PathVariable("guestId") Long id,
                                                               @RequestParam(value = "status", required = false) RequestStatus status) {
        Collection<Request> guestRequests = requestService.findByGuestId(id, status);

        Collection<RequestDTO> guestRequestDTO = guestRequests.stream()
                .map(RequestDTOMapper::fromRequesttoDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<Collection<RequestDTO>>(guestRequestDTO, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('GUEST')")
    public ResponseEntity<RequestDTO> createRequest(@RequestBody RequestDTO requestDTO) throws Exception {
        Request requestModel = RequestDTOMapper.fromDTOtoRequest(requestDTO);
        Request savedRequest = requestService.create(requestModel);
        return new ResponseEntity<RequestDTO>(RequestDTOMapper.fromRequesttoDTO(savedRequest), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RequestDTO> updateRequest(@RequestBody RequestDTO requestDTO, @PathVariable("id") Long id) {
        Request requestForUpdate = requestService.findById(id);
        Request request = RequestDTOMapper.fromDTOtoRequest(requestDTO);
        Request updatedRequest = requestService.update(requestForUpdate, request);
        return new ResponseEntity<RequestDTO>(new RequestDTO(updatedRequest), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('ROLE_GUEST') or hasAuthority('ROLE_HOST')")
    public ResponseEntity<RequestDTO> deleteRequest(@PathVariable("id") Long id) {
        requestService.delete(id);
        return new ResponseEntity<RequestDTO>(HttpStatus.NO_CONTENT);
    }
}