package com.booking.BookingApp.controller;


import com.booking.BookingApp.domain.PricelistItem;
import com.booking.BookingApp.domain.TimeSlot;
import com.booking.BookingApp.domain.enums.RequestStatus;
import com.booking.BookingApp.dto.AccommodationDTO;
import com.booking.BookingApp.dto.RequestDTO;
import com.booking.BookingApp.dto.UserCredentialsDTO;
import com.booking.BookingApp.dto.UserTokenState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cglib.core.Local;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class RequestController1Test {

    private String tokenHost;

    private String tokenGuest;


    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    public void loginHost(){
        ResponseEntity<UserTokenState> responseEntityHost =
                restTemplate.postForEntity("/api/users/login",
                        new UserCredentialsDTO("zika@example.com", "123"),
                        UserTokenState.class);
        tokenHost = responseEntityHost.getBody().getAccessToken();
    }

    @BeforeEach
    public void loginGuest(){
        ResponseEntity<UserTokenState> responseEntityGuest =
                restTemplate.postForEntity("/api/users/login",
                        new UserCredentialsDTO("mika@example.com", "123"),
                        UserTokenState.class);
        tokenGuest = responseEntityGuest.getBody().getAccessToken();
    }

    @Test
    public void automaticConfirmationTest() {
        TimeSlot timeSlot = getValidReservationTimeSlot();

        AccommodationDTO accommodation = new AccommodationDTO();

        Collection<TimeSlot> freeTimeSlots = new ArrayList<>();
        freeTimeSlots.add(getValidFreeTimeSlots());
        accommodation.setFreeTimeSlots(freeTimeSlots);

        accommodation.setMinGuests(1);
        accommodation.setMaxGuests(5);
        accommodation.setId(1L);

        PricelistItem pricelistItem = new PricelistItem(getValidReservationTimeSlot(), 100.0, false);
        ArrayList<PricelistItem> priceList = new ArrayList<>();
        priceList.add(pricelistItem);
        accommodation.setPriceList(priceList);

        HttpHeaders headers = new HttpHeaders();
        RequestDTO requestDTO = new RequestDTO(1L, timeSlot, 600, accommodation, RequestStatus.ACCEPTED, 3);

        headers.add("Authorization", "Bearer " + tokenGuest);

        // Kreiramo zahtev za rezervaciju
        HttpEntity<RequestDTO> httpEntity = new HttpEntity<>(requestDTO, headers);
        ResponseEntity<RequestDTO> responseEntity = restTemplate.postForEntity("/api/requests", httpEntity, RequestDTO.class);

        RequestDTO createdRequest = responseEntity.getBody();

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(RequestStatus.ACCEPTED, createdRequest.getStatus());

        // Izmenimo slobodne termine smestaja
        HttpEntity<TimeSlot> httpEntityUpdate = new HttpEntity<>(timeSlot, headers);
        ResponseEntity<AccommodationDTO> responseEntityUpdate =
                restTemplate.exchange("/api/accommodations/changeFreeTimeSlots/{accommodationId}",
                        HttpMethod.PUT, httpEntityUpdate, AccommodationDTO.class, accommodation.getId());

        AccommodationDTO accommodationResultDTO = responseEntityUpdate.getBody();

        assertEquals(HttpStatus.OK, responseEntityUpdate.getStatusCode());
        assertEquals(2,accommodationResultDTO.getFreeTimeSlots().size());
    }


    @Test
    public void manualConfirmationAcceptingReservationRequestTest() {
        TimeSlot timeSlot = getValidReservationTimeSlot();

        AccommodationDTO accommodation = new AccommodationDTO();
        Collection<TimeSlot>freeTimeSlots=new ArrayList<>();
        freeTimeSlots.add(getValidFreeTimeSlots());
        accommodation.setFreeTimeSlots(freeTimeSlots);

        accommodation.setMinGuests(1);
        accommodation.setMaxGuests(5);
        accommodation.setId(1L);

        PricelistItem pricelistItem = new PricelistItem(getValidReservationTimeSlot(), 100.0, false);
        ArrayList<PricelistItem> priceList = new ArrayList<>();
        priceList.add(pricelistItem);
        accommodation.setPriceList(priceList);

        HttpHeaders headersGuest = new HttpHeaders();
        HttpHeaders headersHost = new HttpHeaders();

        RequestDTO requestDTO = new RequestDTO(1L, timeSlot, 600, accommodation, RequestStatus.PENDING, 3);

        headersGuest.add("Authorization", "Bearer " + tokenGuest);
        headersHost.add("Authorization", "Bearer " + tokenHost);

        HttpEntity<RequestDTO> httpEntityGuest = new HttpEntity<>(requestDTO, headersGuest);
        HttpEntity<RequestDTO> httpEntityHost = new HttpEntity<>(requestDTO, headersHost);

        // Kreiramo rezervaciju
        ResponseEntity<RequestDTO> responseEntity = restTemplate.postForEntity("/api/requests", httpEntityGuest, RequestDTO.class);

        RequestDTO createdRequest = responseEntity.getBody();

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(RequestStatus.PENDING, createdRequest.getStatus());

        // Odobrimo je
        ResponseEntity<RequestDTO> responseEntityAccept =
                restTemplate.exchange("/api/requests/accept/{id}",
                        HttpMethod.PUT, httpEntityHost, RequestDTO.class, createdRequest.getId());

        RequestDTO acceptedRequest = responseEntityAccept.getBody();

        assertEquals(HttpStatus.OK, responseEntityAccept.getStatusCode());
        assertEquals(RequestStatus.ACCEPTED, acceptedRequest.getStatus());
    }


    @Test
    public void manualConfirmationDenyingReservationRequestTest() {
        TimeSlot timeSlot = getValidReservationTimeSlot();

        AccommodationDTO accommodation = new AccommodationDTO();
        Collection<TimeSlot>freeTimeSlots=new ArrayList<>();
        freeTimeSlots.add(getValidFreeTimeSlots());
        accommodation.setFreeTimeSlots(freeTimeSlots);

        accommodation.setMinGuests(1);
        accommodation.setMaxGuests(5);
        accommodation.setId(1L);

        PricelistItem pricelistItem = new PricelistItem(getValidReservationTimeSlot(), 100.0, false);
        ArrayList<PricelistItem> priceList = new ArrayList<>();
        priceList.add(pricelistItem);
        accommodation.setPriceList(priceList);

        HttpHeaders headersGuest = new HttpHeaders();
        HttpHeaders headersHost = new HttpHeaders();

        RequestDTO requestDTO = new RequestDTO(1L, timeSlot, 600, accommodation, RequestStatus.PENDING, 3);

        headersGuest.add("Authorization", "Bearer " + tokenGuest);
        headersHost.add("Authorization", "Bearer " + tokenHost);

        HttpEntity<RequestDTO> httpEntityGuest = new HttpEntity<>(requestDTO, headersGuest);
        HttpEntity<RequestDTO> httpEntityHost = new HttpEntity<>(requestDTO, headersHost);

        // Kreiramo rezervaciju
        ResponseEntity<RequestDTO> responseEntity = restTemplate.postForEntity("/api/requests", httpEntityGuest, RequestDTO.class);

        RequestDTO createdRequest = responseEntity.getBody();

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(RequestStatus.PENDING, createdRequest.getStatus());

        // Odbijamo je
        ResponseEntity<RequestDTO> responseEntityAccept =
                restTemplate.exchange("/api/requests/deny/{id}",
                        HttpMethod.PUT, httpEntityHost, RequestDTO.class, createdRequest.getId());

        RequestDTO deniedRequest = responseEntityAccept.getBody();

        assertEquals(HttpStatus.OK, responseEntityAccept.getStatusCode());
        assertEquals(RequestStatus.DENIED, deniedRequest.getStatus());
    }

    @Test
    public void manualConfirmationWithTwoReservationWithOverlappingDates() {
        TimeSlot reservationTimeSlot1 = getValidReservationTimeSlot();
        TimeSlot reservationTimeSlot2 = getOverlappingReservationTimeSlot();

        AccommodationDTO accommodation = new AccommodationDTO();
        Collection<TimeSlot>freeTimeSlots=new ArrayList<>();
        freeTimeSlots.add(getValidFreeTimeSlots());
        accommodation.setFreeTimeSlots(freeTimeSlots);

        accommodation.setMinGuests(1);
        accommodation.setMaxGuests(5);
        accommodation.setId(1L);

        PricelistItem pricelistItem = new PricelistItem(getValidReservationTimeSlot(), 100.0, false);
        ArrayList<PricelistItem> priceList = new ArrayList<>();
        priceList.add(pricelistItem);
        accommodation.setPriceList(priceList);

        HttpHeaders headersGuest = new HttpHeaders();
        HttpHeaders headersHost = new HttpHeaders();

        RequestDTO requestDTO1 = new RequestDTO(1L, reservationTimeSlot1, 600, accommodation, RequestStatus.PENDING, 3);
        RequestDTO requestDTO2 = new RequestDTO(2L, reservationTimeSlot2, 600, accommodation, RequestStatus.PENDING, 3);

        headersGuest.add("Authorization", "Bearer " + tokenGuest);
        headersHost.add("Authorization", "Bearer " + tokenHost);

        HttpEntity<RequestDTO> httpEntityGuest1 = new HttpEntity<>(requestDTO1, headersGuest);
        HttpEntity<RequestDTO> httpEntityGuest2 = new HttpEntity<>(requestDTO2, headersGuest);
        HttpEntity<RequestDTO> httpEntityHost = new HttpEntity<>(requestDTO1, headersHost);

        // Kreiramo jednan zahtev za rezervaciju
        ResponseEntity<RequestDTO> responseEntity1 = restTemplate.postForEntity("/api/requests", httpEntityGuest1, RequestDTO.class);

        RequestDTO createdRequest1 = responseEntity1.getBody();

        assertEquals(HttpStatus.CREATED, responseEntity1.getStatusCode());
        assertEquals(RequestStatus.PENDING, createdRequest1.getStatus());

        // Kreiramo drugi zahtev za rezervaciju
        ResponseEntity<RequestDTO> responseEntity2 = restTemplate.postForEntity("/api/requests", httpEntityGuest2, RequestDTO.class);

        RequestDTO createdRequest2 = responseEntity2.getBody();

        assertEquals(HttpStatus.CREATED, responseEntity2.getStatusCode());
        assertEquals(RequestStatus.PENDING, createdRequest2.getStatus());

        // Prihvatamo prvi zahtev za rezervaciju
        ResponseEntity<RequestDTO> responseEntityAccept =
                restTemplate.exchange("/api/requests/accept/{id}",
                        HttpMethod.PUT, httpEntityHost, RequestDTO.class, createdRequest1.getId());

        RequestDTO acceptedRequest = responseEntityAccept.getBody();

        assertEquals(HttpStatus.OK, responseEntityAccept.getStatusCode());
        assertEquals(RequestStatus.ACCEPTED, acceptedRequest.getStatus());

        // Proveravamo da li se odbije drugi zahtev za rezervaciju ciji datumi se preklapaju
        ResponseEntity<RequestDTO> responseEntityCheckSecondRequest =
                restTemplate.exchange("/api/requests/{id}",HttpMethod.GET,httpEntityHost
                        ,RequestDTO.class, createdRequest2.getId());
        RequestDTO checkedRequest2 = responseEntityCheckSecondRequest.getBody();

        assertEquals(HttpStatus.OK, responseEntityCheckSecondRequest.getStatusCode());
        assertEquals(RequestStatus.DENIED, checkedRequest2.getStatus());
    }



    private TimeSlot getValidFreeTimeSlots() {  //dates from database

        LocalDate startDate=LocalDate.of(2024,1,25);
        LocalDate endDate=LocalDate.of(2024,1,30);

        return new TimeSlot(startDate, endDate);
    }

    private TimeSlot getValidReservationTimeSlot(){
        LocalDate startDate=LocalDate.of(2024,1,26);
        LocalDate endDate=LocalDate.of(2024,1,28);

        return new TimeSlot(startDate, endDate);
    }

    private TimeSlot getOverlappingReservationTimeSlot(){
        LocalDate startDate=LocalDate.of(2024,1,27);
        LocalDate endDate=LocalDate.of(2024,1,29);

        return new TimeSlot(startDate, endDate);
    }




}
