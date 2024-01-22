package com.booking.BookingApp.repository;


import com.booking.BookingApp.domain.Accommodation;
import com.booking.BookingApp.domain.Request;
import com.booking.BookingApp.domain.TimeSlot;
import com.booking.BookingApp.domain.enums.RequestStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;


@DataJpaTest
@ActiveProfiles("test")
public class RequestRepositoryTest {

    @Autowired
    RequestRepository requestRepository;

    @Autowired
    AccommodationRepository accommodationRepository;

    @Test
    void testFindAllByAccommodationAndTimeSlot() {
        Accommodation accommodation = new Accommodation();
        accommodation.setId(1L);

        LocalDate startDate = LocalDate.now().plusDays(3);
        LocalDate endDate = LocalDate.now().plusDays(5);

        Request request1 = new Request();
        request1.setAccommodation(accommodation);
        request1.setTimeSlot(new TimeSlot(startDate.minusDays(1), endDate.minusDays(1)));
        requestRepository.save(request1);

        Request request2 = new Request();
        request2.setAccommodation(accommodation);
        request2.setTimeSlot(new TimeSlot(startDate, endDate));
        requestRepository.save(request2);

        Collection<Request> result = requestRepository.findAllByAccommodationAndTimeSlot(accommodation, startDate, endDate);

        System.out.println(result);
        assertNotNull(result, "Result should not be null");
        assertEquals(2, result.size(), "There should be only one request in the result");

    }

}

