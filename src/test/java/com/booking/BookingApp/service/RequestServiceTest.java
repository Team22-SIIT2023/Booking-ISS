package com.booking.BookingApp.service;

import com.booking.BookingApp.domain.Accommodation;
import com.booking.BookingApp.domain.Request;
import com.booking.BookingApp.domain.TimeSlot;
import com.booking.BookingApp.domain.enums.RequestStatus;
import com.booking.BookingApp.repository.RequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verifyNoInteractions;

@SpringBootTest
//@ExtendWith(SpringExtension.class)
//@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
public class RequestServiceTest {

    @Mock
    RequestRepository requestRepository;

    @Mock
    AccommodationService accommodationService;

    @Mock
    AvailabilityService availabilityService;

    @InjectMocks
    RequestService requestService;


    @BeforeEach
//    @Sql("classpath:data-h2.sql")
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
//    @Sql("classpath:data-h2.sql")
    public void createRequest_PastDate() throws Exception {
        TimeSlot timeSlot=getDateInPast();
        Accommodation accommodation=new Accommodation();
        accommodation.setMinGuests(1);
        accommodation.setMaxGuests(5);
        Request request = new Request(1L, timeSlot, 200, null, accommodation, 3, RequestStatus.PENDING, false);
        Request result=requestService.create(request);
        assertNull(result);
        verifyNoInteractions(accommodationService);
        verifyNoInteractions(availabilityService);
        verifyNoInteractions(requestRepository);
    }
    @Test
    @Sql("classpath:data-h2.sql")
    public void createRequest_EndBeforeStartDate() throws Exception {
        TimeSlot timeSlot=getEndBeforeStartDate();
        Accommodation accommodation=new Accommodation();
        accommodation.setMinGuests(1);
        accommodation.setMaxGuests(5);
        Request request = new Request(1L, timeSlot, 200, null, accommodation, 3, RequestStatus.PENDING, false);
        Request result=requestService.create(request);
        assertNull(result);
        verifyNoInteractions(accommodationService);
        verifyNoInteractions(availabilityService);
        verifyNoInteractions(requestRepository);
    }

    private TimeSlot getDateInPast() {
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.minusDays(7);
        LocalDate endDate = now.plusDays(3);

        return new TimeSlot(startDate, endDate);
    }
    private TimeSlot getEndBeforeStartDate() {
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.plusDays(5);
        LocalDate endDate = now.plusDays(1);

        return new TimeSlot(startDate, endDate);
}
}
